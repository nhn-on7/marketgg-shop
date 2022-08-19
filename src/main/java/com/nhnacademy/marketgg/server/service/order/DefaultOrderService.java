package com.nhnacademy.marketgg.server.service.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.repository.auth.AuthRepository;
import com.nhnacademy.marketgg.server.constant.PaymentType;
import com.nhnacademy.marketgg.server.delivery.DeliveryRepository;
import com.nhnacademy.marketgg.server.dto.info.AuthInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoRequest;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoResponse;
import com.nhnacademy.marketgg.server.dto.request.order.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.order.OrderInfoRequestDto;
import com.nhnacademy.marketgg.server.dto.request.order.OrderUpdateStatusRequest;
import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderFormResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderGivenCoupon;
import com.nhnacademy.marketgg.server.dto.response.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;
import com.nhnacademy.marketgg.server.dto.response.orderproduct.OrderProductResponse;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.OrderProduct;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.event.OrderCouponCanceledEvent;
import com.nhnacademy.marketgg.server.entity.event.OrderPointCanceledEvent;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotOverMinimumMoneyException;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotValidException;
import com.nhnacademy.marketgg.server.exception.deliveryaddresses.DeliveryAddressNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.order.OrderMemberNotMatchedException;
import com.nhnacademy.marketgg.server.exception.order.OrderNotFoundException;
import com.nhnacademy.marketgg.server.exception.pointhistory.PointNotEnoughException;
import com.nhnacademy.marketgg.server.exception.product.ProductStockNotEnoughException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.repository.deliveryaddress.DeliveryAddressRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.orderproduct.OrderProductRepository;
import com.nhnacademy.marketgg.server.repository.pointhistory.PointHistoryRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import com.nhnacademy.marketgg.server.service.cart.CartProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nhnacademy.marketgg.server.auth.AuthAdapter.checkResult;

@Service
@RequiredArgsConstructor
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final AuthRepository authRepository;
    private final OrderProductRepository orderProductRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final DeliveryRepository deliveryRepository;
    private final PointHistoryRepository pointRepository;
    private final CouponRepository couponRepository;
    private final UsedCouponRepository usedCouponRepository;
    private final GivenCouponRepository givenCouponRepository;
    private final ProductRepository productRepository;
    private final CartProductService cartProductService;
    private final ApplicationEventPublisher publisher;

    @Transactional
    @Override
    public OrderToPayment createOrder(final OrderCreateRequest orderRequest, final MemberInfo memberInfo) throws JsonProcessingException {
        int i = 0;
        Member member = memberRepository.findById(memberInfo.getId()).orElseThrow(MemberNotFoundException::new);
        MemberInfoResponse memberResponse = checkResult(
                authRepository.getMemberInfo(new MemberInfoRequest(member.getUuid())));
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(orderRequest.getDeliveryAddressId())
                                                                   .orElseThrow(DeliveryAddressNotFoundException::new);
        Order order = new Order(member, deliveryAddress, orderRequest);
        List<Long> productIds = orderRequest.getProducts().stream().map(ProductToOrder::getId)
                                            .collect(Collectors.toList());
        List<Integer> productAmounts = orderRequest.getProducts().stream().map(ProductToOrder::getAmount)
                                                   .collect(Collectors.toList());
        List<Product> products = productRepository.findByIds(productIds);

        checkOrderValid(orderRequest, memberResponse, member.getId());
        orderRepository.save(order);

        for (Product product : products) {
            long remain = product.getTotalStock() - productAmounts.get(i);
            if (remain < 0) {
                throw new ProductStockNotEnoughException();
            }
            product.updateTotalStock(remain);
            productRepository.save(product);
            orderProductRepository.save(new OrderProduct(order, product, productAmounts.get(i++)));
        }
        cartProductService.deleteProducts(memberInfo, productIds);

        return makeOrderToPayment(order, orderRequest);
    }

    private OrderToPayment makeOrderToPayment(final Order order, final OrderCreateRequest orderRequest) {
        List<ProductToOrder> products = orderRequest.getProducts();
        String orderId = attachPrefix(order.getId());
        String orderName = products.get(0).getName() + " 외 " + products.size() + "건";

        return new OrderToPayment(orderId, orderName, orderRequest.getName(), orderRequest.getEmail(),
                                  orderRequest.getTotalAmount(), orderRequest.getCouponId(),
                                  orderRequest.getUsedPoint(), orderRequest.getExpectedSavePoint());
    }

    private void checkOrderValid(final OrderCreateRequest orderRequest, final MemberInfoResponse memberResponse,
                                 final Long memberId) {
        if (!memberResponse.getEmail().equals(orderRequest.getEmail())) {
            throw new OrderMemberNotMatchedException();
        }
        Optional<Coupon> coupon = checkCouponValid(orderRequest.getCouponId());
        if (coupon.isPresent() && coupon.get().getMinimumMoney() > orderRequest.getTotalOrigin()) {
            throw new CouponNotOverMinimumMoneyException();
        }
        if (pointRepository.findLastTotalPoints(memberId) < orderRequest.getUsedPoint()) {
            throw new PointNotEnoughException();
        }
    }

    private Optional<Coupon> checkCouponValid(final Long couponId) {
        if (Objects.isNull(couponId)) {
            return Optional.empty();
        }
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CouponNotFoundException::new);
        if (usedCouponRepository.existsCouponId(couponId)) {
            throw new CouponNotValidException();
        }
        return Optional.of(coupon);
    }

    @Override
    public OrderFormResponse retrieveOrderForm(final List<ProductToOrder> products, final MemberInfo memberInfo,
                                               final AuthInfo authInfo) {

        Long memberId = memberInfo.getId();
        List<OrderGivenCoupon> orderGivenCoupons = givenCouponRepository.findOwnCouponsByMemberId(memberId);
        Integer totalPoint = pointRepository.findLastTotalPoints(memberId);
        List<DeliveryAddressResponse> deliveryAddresses = deliveryAddressRepository.findDeliveryAddressesByMemberId(
                memberId);
        List<String> paymentTypes = Arrays.stream(PaymentType.values())
                                          .map(PaymentType::getType)
                                          .collect(Collectors.toList());

        return OrderFormResponse.builder()
                                .products(products)
                                .memberId(memberId).memberName(authInfo.getName())
                                .memberEmail(authInfo.getEmail()).memberGrade(memberInfo.getMemberGrade())
                                /*.haveGgpass()*/
                                .givenCouponList(orderGivenCoupons)
                                .totalPoint(totalPoint)
                                .deliveryAddressList(deliveryAddresses)
                                .paymentType(paymentTypes)
                                .totalOrigin(calculateTotalOrigin(products))
                                .build();
    }

    private Long calculateTotalOrigin(final List<ProductToOrder> products) {
        Long result = 0L;

        for (ProductToOrder product : products) {
            result += product.getPrice();
        }

        return result;
    }

    @Override
    public List<OrderRetrieveResponse> retrieveOrderList(final MemberInfo memberinfo) {
        return orderRepository.findOrderList(memberinfo.getId(), memberinfo.isUser());
    }

    @Override
    public OrderDetailRetrieveResponse retrieveOrderDetail(final Long orderId, final MemberInfo memberInfo) {
        OrderDetailRetrieveResponse detailResponse = orderRepository.findOrderDetail(orderId, memberInfo.getId(),
                                                                                     memberInfo.isUser());
        List<OrderProductResponse> productResponses = orderProductRepository.findByOrderId(orderId);
        detailResponse.addOrderDetail(productResponses);

        return detailResponse;
    }

    @Transactional
    @Override
    public void updateStatus(final Long orderId, final OrderUpdateStatusRequest status) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);

        order.updateStatus(status.getStatus());

        orderRepository.save(order);
    }

    @Override
    public void createTrackingNo(final Long orderId) throws JsonProcessingException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        String uuid = memberRepository.findUuidByOrderId(orderId);
        MemberInfoResponse memberResponse = checkResult(authRepository.getMemberInfo(new MemberInfoRequest(uuid)));

        OrderInfoRequestDto orderRequest = new OrderInfoRequestDto(memberResponse.getName(), order.getAddress(),
                                                                   order.getDetailAddress(),
                                                                   memberResponse.getPhoneNumber(),
                                                                   String.valueOf(orderId));

        deliveryRepository.createTrackingNo(orderRequest);
    }

    @Transactional
    @Override
    public void cancelOrder(final Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);

        order.cancel();
        orderRepository.save(order);

        publisher.publishEvent(new OrderPointCanceledEvent(order));
        publisher.publishEvent(new OrderCouponCanceledEvent(order));
    }

}
