package com.nhnacademy.marketgg.server.service.order;

import com.nhnacademy.marketgg.server.constant.PaymentType;
import com.nhnacademy.marketgg.server.dto.info.AuthInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.order.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderFormResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderGivenCoupon;
import com.nhnacademy.marketgg.server.dto.response.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.OrderProduct;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.deliveryaddress.DeliveryAddressRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.orderproduct.OrderProductRepository;
import com.nhnacademy.marketgg.server.repository.pointhistory.PointHistoryRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final OrderProductRepository orderProductRepository;
    private final DeliveryAddressRepository deliveryRepository;
    private final PointHistoryRepository pointRepository;
    private final UsedCouponRepository usedCouponRepository;
    private final GivenCouponRepository givenCouponRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public OrderToPayment createOrder(final OrderCreateRequest orderRequest, final Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Order order = new Order(member, orderRequest);
        // Product product = productRepository.find
        // OrderProduct orderProduct = new OrderProduct();
        // memo: 배송지 조회

        orderRepository.save(order);
        // Memo: 주문배송지, 주문상품 save


        // return new OrderToPayment();
        return null;
    }

    @Override
    public OrderFormResponse retrieveOrderForm(final List<ProductToOrder> products, final MemberInfo memberInfo,
                                               final AuthInfo authInfo) {

        Long memberId = memberInfo.getId();
        List<OrderGivenCoupon> orderGivenCoupons = givenCouponRepository.findOwnCouponsByMemberId(memberId);
        AtomicReference<Integer> totalPoint = new AtomicReference<>(0);
        pointRepository.findLastTotalPoint(memberId)
                       .ifPresent(x -> totalPoint.set(x.getTotalPoint()));
        // memo: 배송지 조회
        List<String> paymentTypes = Arrays.stream(PaymentType.values())
                                          .map(PaymentType::getType)
                                          .collect(Collectors.toList());
        // memo: ggpass 가 완성되어 있지않음
        return OrderFormResponse.builder()
                                .products(products)
                                .memberId(memberId).memberName(authInfo.getName())
                                .memberEmail(authInfo.getEmail()).memberGrade(memberInfo.getMemberGrade())
                                /*.haveGgpass()*/
                                .givenCouponList(orderGivenCoupons)
                                .totalPoint(totalPoint.get())
                                .deliveryAddressId(null) // memo: 배송지 조회하고 수정하기
                                .zipCode(null)
                                .address(null)
                                .detailAddress(null)
                                .isDefault(true)
                                .paymentType(paymentTypes)
                                .totalOrigin(calculateTotalOrigin(products))
                                .build();
    }

    // memo: 원가 계산
    private Long calculateTotalOrigin(List<ProductToOrder> products) {
        Long result = 0L;

        for (ProductToOrder product : products) {
            result += product.getPrice();
        }

        return result;
    }

    // 주문 목록 조회 - 관리자(전체), 회원(본인)
    @Override
    public List<OrderRetrieveResponse> retrieveOrderList(final MemberInfo memberinfo) {
        // return (isUser ? orderRepository.findOrderListById(memberId) : orderRepository.findAllOrder());
        return orderRepository.findOrderList(memberinfo.getId(), memberinfo.isUser());
    }

    @Override
    public OrderDetailRetrieveResponse retrieveOrderDetail(final Long orderId, final MemberInfo memberInfo) {
        return orderRepository.findOrderDetail(orderId, memberInfo.getId(), memberInfo.isUser());
    }

    @Transactional(readOnly = true)
    @Override
    public void deleteOrder(final Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
