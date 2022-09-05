package com.nhnacademy.marketgg.server.service.order;

import static com.nhnacademy.marketgg.server.repository.auth.AuthAdapter.checkResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.constant.OrderStatus;
import com.nhnacademy.marketgg.server.constant.payment.PaymentType;
import com.nhnacademy.marketgg.server.dto.info.AuthInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoRequest;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoResponse;
import com.nhnacademy.marketgg.server.dto.request.order.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.order.OrderInfoRequestDto;
import com.nhnacademy.marketgg.server.dto.request.order.OrderUpdateStatusRequest;
import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.dto.response.coupon.UsedCouponResponse;
import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderFormResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderGivenCoupon;
import com.nhnacademy.marketgg.server.dto.response.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.OrderProduct;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import com.nhnacademy.marketgg.server.eventlistener.event.order.OrderCouponCanceledEvent;
import com.nhnacademy.marketgg.server.eventlistener.event.order.OrderPointCanceledEvent;
import com.nhnacademy.marketgg.server.exception.coupon.CouponIsAlreadyUsedException;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotOverMinimumMoneyException;
import com.nhnacademy.marketgg.server.exception.deliveryaddresses.DeliveryAddressNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.order.OrderMemberNotMatchedException;
import com.nhnacademy.marketgg.server.exception.order.OrderNotFoundException;
import com.nhnacademy.marketgg.server.exception.pointhistory.PointNotEnoughException;
import com.nhnacademy.marketgg.server.exception.product.ProductStockNotEnoughException;
import com.nhnacademy.marketgg.server.repository.auth.AuthRepository;
import com.nhnacademy.marketgg.server.repository.cart.CartProductRepository;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.repository.delivery.DeliveryRepository;
import com.nhnacademy.marketgg.server.repository.deliveryaddress.DeliveryAddressRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.orderproduct.OrderProductRepository;
import com.nhnacademy.marketgg.server.repository.pointhistory.PointHistoryRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import com.nhnacademy.marketgg.server.service.cart.CartProductService;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 기본적인 주문 서비스 기능을 수행합니다.
 */
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
    private final CartProductRepository cartProductRepository;
    private final ApplicationEventPublisher publisher;
    @Value("${gg.eggplant.success-host}")
    private String successHost;

    /**
     * {@inheritDoc}
     *
     * @param orderRequest - 주문을 등록하기 위한 정보를 담은 DTO 입니다.
     * @param memberInfo   - 주문을 등록하는 회원의 정보입니다.
     * @return 결제 요청시에 필요한 정보들을 담은 DTO 를 반환합니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     */
    @Transactional
    @Override
    public OrderToPayment createOrder(final OrderCreateRequest orderRequest, final MemberInfo memberInfo)
        throws JsonProcessingException {

        // MEMO: 주문하는 회원 찾기
        Member member = memberRepository.findById(memberInfo.getId())
                                        .orElseThrow(MemberNotFoundException::new);

        // MEMO: 주문하는 회원 정보 auth server 조회
        MemberInfoResponse memberResponse
            = checkResult(authRepository.getMemberInfo(new MemberInfoRequest(member.getUuid())));

        // MEMO: 주문한 회원이 선택한 배송지 조회
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(orderRequest.getDeliveryAddressId())
                                                                   .orElseThrow(DeliveryAddressNotFoundException::new);
        // MEMO: 주문서 폼에서 받은 주문할 상품 ID 목록
        List<Long> productIds = orderRequest.getProductIds();

        // MEMO: 받아온 상품 ID로 장바구니에 담긴 상품 확인 및 정보 조회 (id, name, price, amount)
        List<ProductToOrder> cartProducts = cartProductRepository.findCartProductsByProductIds(memberInfo.getCart()
                                                                                                         .getId(),
                                                                                               productIds);

        List<Product> products = productRepository.findByIds(productIds);

        // MEMO: 주문 엔티티 생성 및 검증 후 데이터 저장
        Order order = new Order(member, deliveryAddress, orderRequest, products.get(0).getName(), products.size());
        Long memberId = member.getId();
        GivenCoupon givenCoupon = this.checkOrderValid(orderRequest, memberResponse, memberId);
        orderRepository.saveAndFlush(order);

        // MEMO: 사용 쿠폰 적용
        UsedCoupon usedCoupon = UsedCoupon.builder()
                                          .pk(new UsedCoupon.Pk(order.getId(),
                                                                givenCoupon.getPk().getCouponId(),
                                                                memberId))
                                          .order(order)
                                          .givenCoupon(givenCoupon)
                                          .build();

        usedCouponRepository.save(usedCoupon);

        int usedPoint = -orderRequest.getUsedPoint();
        pointRepository.save(new PointHistory(member, order,
                                              pointRepository.findLastTotalPoints(memberId) + usedPoint,
                                              new PointHistoryRequest(usedPoint, "포인트 사용")));

        // MEMO: 재고 차감을 위해 상품 수량만 뽑아내기
        List<Integer> productAmounts = cartProducts.stream()
                                                   .map(ProductToOrder::getAmount)
                                                   .collect(Collectors.toList());

        // MEMO: 상품 재고량 수정, 주문상품 테이블 컬럼 추가
        int i = 0;
        for (Product product : products) {
            long remain = product.getTotalStock() - productAmounts.get(i);
            if (remain < 0) {
                throw new ProductStockNotEnoughException();
            }
            product.updateTotalStock(remain);
            productRepository.save(product);
            orderProductRepository.save(new OrderProduct(order, product, productAmounts.get(i++)));
        }

        // MEMO: 장바구니 목록 주문한 상품 삭제
        cartProductService.deleteProducts(memberInfo, productIds);

        return makeOrderToPayment(order, orderRequest);
    }

    private OrderToPayment makeOrderToPayment(final Order order, final OrderCreateRequest orderRequest) {
        String orderId = attachPrefix(order.getId());

        return new OrderToPayment(orderId, order.getOrderName(), orderRequest.getName(), orderRequest.getEmail(),
                                  orderRequest.getTotalAmount(), orderRequest.getCouponId(),
                                  orderRequest.getUsedPoint(), orderRequest.getExpectedSavePoint());
    }

    /**
     * 쿠폰 및 포인트 내역이 최총 결제 금액 적용에 유효한지 체크합니다.
     *
     * @param orderRequest   주문 요청 객체
     * @param memberResponse 주문한 회원 정보
     * @param memberId       주문한 회원 아이디
     */
    private GivenCoupon checkOrderValid(final OrderCreateRequest orderRequest, final MemberInfoResponse memberResponse,
                                        final Long memberId) {
        if (!memberResponse.getEmail().equals(orderRequest.getEmail())) {
            throw new OrderMemberNotMatchedException();
        }
        Optional<GivenCoupon> givenCoupon = this.checkCouponValid(orderRequest.getCouponId(), memberId);
        if (givenCoupon.isPresent() && (givenCoupon.get()
                                                   .getCoupon()
                                                   .getMinimumMoney() > orderRequest.getTotalOrigin())) {
            throw new CouponNotOverMinimumMoneyException();
        }
        if (pointRepository.findLastTotalPoints(memberId) < orderRequest.getUsedPoint()) {
            throw new PointNotEnoughException();
        }

        return givenCoupon.get();
    }

    private Optional<GivenCoupon> checkCouponValid(final Long couponId, final Long memberId) {
        if (Objects.isNull(couponId)) {
            return Optional.empty();
        }
        GivenCoupon givenCoupon = givenCouponRepository.findById(new GivenCoupon.Pk(couponId, memberId))
                                                       .orElseThrow(CouponNotFoundException::new);

        if (usedCouponRepository.existsCouponId(couponId)) {
            throw new CouponIsAlreadyUsedException();
        }

        return Optional.of(givenCoupon);
    }

    /**
     * {@inheritDoc}
     *
     * @param productIds - 주문할 상품 ID 목록입니다.
     * @param memberInfo - 주문하는 회원의 정보입니다.
     * @param authInfo   - 주문하는 회원의 auth 정보입니다.
     * @return 취합한 정보를 반환합니다.
     */
    @Override
    public OrderFormResponse retrieveOrderForm(final List<Long> productIds, final MemberInfo memberInfo,
                                               final AuthInfo authInfo) {

        Long memberId = memberInfo.getId();
        List<OrderGivenCoupon> orderGivenCoupons = givenCouponRepository.findOwnCouponsByMemberId(memberId);
        Integer totalPoint = pointRepository.findLastTotalPoints(memberId);
        List<DeliveryAddressResponse> deliveryAddresses = deliveryAddressRepository.findDeliveryAddressesByMemberId(
            memberId);
        List<String> paymentTypes = Arrays.stream(PaymentType.values())
                                          .map(PaymentType::getType)
                                          .collect(Collectors.toList());
        List<ProductToOrder> cartProducts =
            cartProductRepository.findCartProductsByProductIds(memberInfo.getCart().getId(), productIds);

        return OrderFormResponse.builder()
                                .products(cartProducts)
                                .memberId(memberId).memberName(authInfo.getName())
                                .memberEmail(authInfo.getEmail()).memberGrade(memberInfo.getMemberGrade())
                                .givenCouponList(orderGivenCoupons)
                                .totalPoint(totalPoint)
                                .deliveryAddressList(deliveryAddresses)
                                .paymentType(paymentTypes)
                                .totalOrigin(calculateTotalOrigin(cartProducts))
                                .build();
    }

    private Long calculateTotalOrigin(final List<ProductToOrder> products) {
        Long totalPrice = 0L;

        for (ProductToOrder product : products) {
            totalPrice += product.getPrice();
        }

        return totalPrice;
    }

    /**
     * {@inheritDoc}
     *
     * @param memberInfo - 주문 목록을 조회하는 회원의 정보입니다.
     * @return 조회하는 회원의 종류에 따라 목록을 List 로 반환합니다.
     */
    @Override
    public Page<OrderRetrieveResponse> retrieveOrderList(final MemberInfo memberInfo, final Pageable pageable) {
        return orderRepository.findOrderList(memberInfo.getId(), memberInfo.isAdmin(), pageable);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId    - 조회할 주문의 식별번호입니다.
     * @param memberInfo - 주문 상세를 조회할 회원의 정보입니다.
     * @return 조회하는 회원의 종류에 따라 상세 조회 정보를 반환합니다.
     */
    @Override
    public OrderDetailRetrieveResponse retrieveOrderDetail(final Long orderId, final MemberInfo memberInfo) {
        OrderDetailRetrieveResponse detailResponse = orderRepository.findOrderDetail(orderId, memberInfo.getId(),
                                                                                     memberInfo.isAdmin());
        List<ProductToOrder> products = orderProductRepository.findByOrderId(orderId);
        UsedCouponResponse couponName = usedCouponRepository.findUsedCouponName(orderId);
        detailResponse.addOrderDetail(products, couponName);

        return detailResponse;
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId - 변경할 주문의 식별번호입니다.
     * @param status  - 변경할 상태값입니다.
     */
    @Transactional
    @Override
    public void updateStatus(final Long orderId,
                             final OrderUpdateStatusRequest status) {

        Order order = orderRepository.findById(orderId)
                                     .orElseThrow(OrderNotFoundException::new);

        order.updateStatus(checkDeliveryStatus(status.getStatus()));

        orderRepository.save(order);
    }

    private String checkDeliveryStatus(String status) {
        String newStatus = status;

        if (status.equals("READY")) {
            newStatus = OrderStatus.DELIVERY_WAITING.getStatus();
        }

        if (status.equals("DELIVERING")) {
            newStatus = OrderStatus.DELIVERY_SHIPPING.getStatus();
        }

        if (status.equals("ARRIVAL")) {
            newStatus = OrderStatus.DELIVERY_COMPLETE.getStatus();
        }

        return newStatus;
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId - 운송장 번호를 발급받을 주문의 식별번호입니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     */
    @Override
    public void createTrackingNo(final Long orderId) throws JsonProcessingException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        String uuid = memberRepository.findUuidByOrderId(orderId);
        MemberInfoResponse memberResponse = checkResult(authRepository.getMemberInfo(new MemberInfoRequest(uuid)));

        OrderInfoRequestDto orderRequest = new OrderInfoRequestDto(memberResponse.getName(), order.getAddress(),
                                                                   order.getDetailAddress(),
                                                                   memberResponse.getPhoneNumber(),
                                                                   String.valueOf(orderId),
                                                                   successHost);

        deliveryRepository.createTrackingNo(orderRequest);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId - 취소할 주문의 식별번호입니다.
     */
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
