package com.nhnacademy.marketgg.server.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.repository.delivery.DeliveryRepository;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.AuthInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoRequest;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoResponse;
import com.nhnacademy.marketgg.server.dto.request.DefaultPageRequest;
import com.nhnacademy.marketgg.server.dto.request.order.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.order.OrderInfoRequestDto;
import com.nhnacademy.marketgg.server.dto.request.order.OrderUpdateStatusRequest;
import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderFormResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderGivenCoupon;
import com.nhnacademy.marketgg.server.dto.response.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.OrderProduct;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.eventlistener.event.order.OrderCouponCanceledEvent;
import com.nhnacademy.marketgg.server.eventlistener.event.order.OrderPointCanceledEvent;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotOverMinimumMoneyException;
import com.nhnacademy.marketgg.server.exception.coupon.CouponIsAlreadyUsedException;
import com.nhnacademy.marketgg.server.exception.order.OrderMemberNotMatchedException;
import com.nhnacademy.marketgg.server.exception.pointhistory.PointNotEnoughException;
import com.nhnacademy.marketgg.server.exception.product.ProductStockNotEnoughException;
import com.nhnacademy.marketgg.server.repository.auth.AuthRepository;
import com.nhnacademy.marketgg.server.repository.cart.CartProductRepository;
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
import com.nhnacademy.marketgg.server.service.order.DefaultOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultOrderServiceTest {

    @InjectMocks
    DefaultOrderService orderService;

    @Mock
    ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);

    @Mock
    OrderRepository orderRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    DeliveryAddressRepository deliveryAddressRepository;

    @Mock
    DeliveryRepository deliveryRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CouponRepository couponRepository;

    @Mock
    UsedCouponRepository usedCouponRepository;

    @Mock
    OrderProductRepository orderProductRepository;

    @Mock
    CartProductService cartProductService;

    @Mock
    CartProductRepository cartProductRepository;

    @Mock
    PointHistoryRepository pointRepository;

    @Mock
    GivenCouponRepository givenCouponRepository;

    @Mock
    AuthRepository authRepository;

    Member member;
    MemberInfoRequest memberInfoRequest;
    MemberInfoResponse memberInfoResponse;
    ShopResult<MemberInfoResponse> shopResult;
    DeliveryAddress deliveryAddress;
    Order order;
    List<Long> productIds;
    Product product;
    MemberInfo memberInfo;
    AuthInfo authInfo;
    ShopResult<AuthInfo> authInfoResponse;
    Coupon coupon;

    @BeforeEach
    void setUp() {
        member = Dummy.getDummyMember(Dummy.getDummyCart(1L));
        memberInfoRequest = new MemberInfoRequest(Dummy.DUMMY_UUID);
        memberInfoResponse = new MemberInfoResponse("KimDummy", "KimDummy@dooray.com",
                                                    "010-1111-1111");
        shopResult = ShopResult.successWith(memberInfoResponse);
        deliveryAddress = Dummy.getDummyDeliveryAddress();
        order = Dummy.getDummyOrder();
        productIds = List.of(1L);
        product = Dummy.getDummyProduct(1L);
        memberInfo = Dummy.getDummyMemberInfo(1L, Dummy.getDummyCart(1L));
        coupon = Dummy.getDummyCoupon();
        authInfo = Dummy.getDummyAuthInfo();
        authInfoResponse = ShopResult.successWith(authInfo);
    }

    // @Test
    // @DisplayName("주문 등록(쿠폰을 사용했을 경우)")
    // void testCreateOrderUsedCoupon() throws JsonProcessingException {
    //     given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
    //     given(authRepository.getMemberInfo(any(MemberInfoRequest.class))).willReturn(shopResult);
    //     given(deliveryAddressRepository.findById(anyLong())).willReturn(Optional.of(deliveryAddress));
    //     given(cartProductRepository.findCartProductsByProductIds(anyLong(), anyList()))
    //             .willReturn(List.of(Dummy.getDummyProductToOrder()));
    //     given(productRepository.findByIds(productIds)).willReturn(List.of(product));
    //     given(orderRepository.save(any(Order.class))).willReturn(order);
    //     given(productRepository.save(any(Product.class))).willReturn(product);
    //     given(orderProductRepository.save(any(OrderProduct.class))).willReturn(Dummy.getDummyOrderProduct());
    //     given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));
    //     given(usedCouponRepository.existsCouponId(anyLong())).willReturn(false);
    //     given(pointRepository.findLastTotalPoints(any())).willReturn(20000);
    //
    //     willDoNothing().given(cartProductService).deleteProducts(memberInfo, productIds);
    //
    //     orderService.createOrder(Dummy.getDummyOrderCreateRequest(), memberInfo);
    //
    //     then(memberRepository).should(times(1)).findById(anyLong());
    //     then(authRepository).should(times(1)).getMemberInfo(any(MemberInfoRequest.class));
    //     then(deliveryAddressRepository).should(times(1)).findById(anyLong());
    //     then(productRepository).should(times(1)).findByIds(anyList());
    //     then(usedCouponRepository).should(times(1)).existsCouponId(anyLong());
    // }

    // @Test
    // @DisplayName("주문 등록(쿠폰을 사용하지 않은 경우)")
    // void testCreateOrderNotUsedCoupon() throws JsonProcessingException {
    //     OrderCreateRequest orderCreateRequest = Dummy.getDummyOrderCreateRequest();
    //     ReflectionTestUtils.setField(orderCreateRequest, "couponId" , null);
    //
    //     given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
    //     given(authRepository.getMemberInfo(any(MemberInfoRequest.class))).willReturn(shopResult);
    //     given(deliveryAddressRepository.findById(anyLong())).willReturn(Optional.of(deliveryAddress));
    //     given(cartProductRepository.findCartProductsByProductIds(anyLong(), anyList()))
    //             .willReturn(List.of(Dummy.getDummyProductToOrder()));
    //     given(productRepository.findByIds(productIds)).willReturn(List.of(product));
    //     given(orderRepository.save(any(Order.class))).willReturn(order);
    //     given(productRepository.save(any(Product.class))).willReturn(product);
    //     given(orderProductRepository.save(any(OrderProduct.class))).willReturn(Dummy.getDummyOrderProduct());
    //     given(pointRepository.findLastTotalPoints(any())).willReturn(20000);
    //
    //     willDoNothing().given(cartProductService).deleteProducts(memberInfo, productIds);
    //
    //     orderService.createOrder(orderCreateRequest, memberInfo);
    //
    //     then(usedCouponRepository).should(times(0)).existsCouponId(anyLong());
    // }

    // @Test
    // @DisplayName("주문 등록 시 주문 상품 재고량 부족")
    // void testCreateOrderFailWhenOrderProductStockNotEnough() throws JsonProcessingException {
    //     ReflectionTestUtils.setField(product, "totalStock", 1L);
    //
    //     given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
    //     given(authRepository.getMemberInfo(any(MemberInfoRequest.class))).willReturn(shopResult);
    //     given(deliveryAddressRepository.findById(anyLong())).willReturn(Optional.of(deliveryAddress));
    //     given(cartProductRepository.findCartProductsByProductIds(anyLong(), anyList()))
    //             .willReturn(List.of(Dummy.getDummyProductToOrder()));
    //     given(productRepository.findByIds(productIds)).willReturn(List.of(product));
    //     given(orderRepository.save(any(Order.class))).willReturn(order);
    //     given(productRepository.save(any(Product.class))).willReturn(product);
    //     given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));
    //     given(usedCouponRepository.existsCouponId(anyLong())).willReturn(false);
    //     given(pointRepository.findLastTotalPoints(any())).willReturn(20000);
    //
    //     orderService.createOrder(Dummy.getDummyOrderCreateRequest(), memberInfo);
    //
    //     assertThatThrownBy(() -> orderService.createOrder(Dummy.getDummyOrderCreateRequest(), memberInfo))
    //             .isInstanceOf(ProductStockNotEnoughException.class);
    // }

    @Test
    @DisplayName("주문 등록 시 주문자 정보와 로그인한 회원 정보 불일치")
    void testCreateOrderFailWhenNotMatchedOrderingPersonAndLoggedInMember() throws JsonProcessingException {
        ReflectionTestUtils.setField(memberInfoResponse, "email", "strange@dooray.com");
        ShopResult<MemberInfoResponse> strange = ShopResult.successWith(memberInfoResponse);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(authRepository.getMemberInfo(any(MemberInfoRequest.class))).willReturn(strange);
        given(deliveryAddressRepository.findById(anyLong())).willReturn(Optional.of(deliveryAddress));
        given(cartProductRepository.findCartProductsByProductIds(anyLong(), anyList()))
                .willReturn(List.of(Dummy.getDummyProductToOrder()));
        given(productRepository.findByIds(productIds)).willReturn(List.of(product));

        assertThatThrownBy(() -> orderService.createOrder(Dummy.getDummyOrderCreateRequest(), memberInfo))
                .isInstanceOf(OrderMemberNotMatchedException.class);
    }

    // @Test
    // @DisplayName("주문 등록 시 쿠폰 사용 최소 금액을 채우지 못한 경우")
    // void testCreateOrderFailWhenNotOverCouponMinimumMoney() throws JsonProcessingException {
    //     OrderCreateRequest orderCreateRequest = Dummy.getDummyOrderCreateRequest();
    //     ReflectionTestUtils.setField(orderCreateRequest, "totalOrigin", 10L);
    //     ReflectionTestUtils.setField(orderCreateRequest, "usedPoint", 0);
    //
    //     given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
    //     given(authRepository.getMemberInfo(any(MemberInfoRequest.class))).willReturn(shopResult);
    //     given(deliveryAddressRepository.findById(anyLong())).willReturn(Optional.of(deliveryAddress));
    //     given(cartProductRepository.findCartProductsByProductIds(anyLong(), anyList()))
    //             .willReturn(List.of(Dummy.getDummyProductToOrder()));
    //     given(productRepository.findByIds(productIds)).willReturn(List.of(product));
    //     given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));
    //     given(usedCouponRepository.existsCouponId(anyLong())).willReturn(false);
    //
    //     assertThatThrownBy(() -> orderService.createOrder(orderCreateRequest, memberInfo))
    //             .isInstanceOf(CouponNotOverMinimumMoneyException.class);
    // }

    // @Test
    // @DisplayName("주문 등록 시 보유 포인트보다 많은 사용 포인트 입력")
    // void testCreateOrderFailWhenPointNotEnough() throws JsonProcessingException {
    //     given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
    //     given(authRepository.getMemberInfo(any(MemberInfoRequest.class))).willReturn(shopResult);
    //     given(deliveryAddressRepository.findById(anyLong())).willReturn(Optional.of(deliveryAddress));
    //     given(cartProductRepository.findCartProductsByProductIds(anyLong(), anyList()))
    //             .willReturn(List.of(Dummy.getDummyProductToOrder()));
    //     given(productRepository.findByIds(productIds)).willReturn(List.of(product));
    //     given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));
    //     given(usedCouponRepository.existsCouponId(anyLong())).willReturn(false);
    //     given(pointRepository.findLastTotalPoints(any())).willReturn(100);
    //
    //     assertThatThrownBy(() -> orderService.createOrder(Dummy.getDummyOrderCreateRequest(), memberInfo))
    //             .isInstanceOf(PointNotEnoughException.class);
    // }

    // @Test
    // @DisplayName("주문 등록 시 이미 사용된 쿠폰을 사용함")
    // void testCreateOrderFailWhenUsedCouponIsAlreadyUsed() throws JsonProcessingException {
    //     given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
    //     given(authRepository.getMemberInfo(any(MemberInfoRequest.class))).willReturn(shopResult);
    //     given(deliveryAddressRepository.findById(anyLong())).willReturn(Optional.of(deliveryAddress));
    //     given(cartProductRepository.findCartProductsByProductIds(anyLong(), anyList()))
    //             .willReturn(List.of(Dummy.getDummyProductToOrder()));
    //     given(productRepository.findByIds(productIds)).willReturn(List.of(product));
    //     given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));
    //     given(usedCouponRepository.existsCouponId(anyLong())).willReturn(true);
    //
    //     assertThatThrownBy(() -> orderService.createOrder(Dummy.getDummyOrderCreateRequest(), memberInfo))
    //             .isInstanceOf(CouponIsAlreadyUsedException.class);
    // }

    // @Test
    // @DisplayName("주문서 폼 필요정보 조회")
    // void testRetrieveOrderForm() {
    //     List<Long> productIds = List.of(1L);
    //     List<OrderGivenCoupon> givenCoupons = List.of(Dummy.getGivenCouponResponse());
    //     List<DeliveryAddressResponse> deliveryAddressResponses = List.of(Dummy.getDummyDeliveryAddressResponse());
    //
    //     given(givenCouponRepository.findOwnCouponsByMemberId(anyLong())).willReturn(givenCoupons);
    //     given(pointRepository.findLastTotalPoints(anyLong())).willReturn(20000);
    //     given(deliveryAddressRepository.findDeliveryAddressesByMemberId(anyLong())).willReturn(
    //             deliveryAddressResponses);
    //     given(cartProductRepository.findCartProductsByProductIds(anyLong(), anyList()))
    //             .willReturn(List.of(Dummy.getDummyProductToOrder()));
    //
    //     OrderFormResponse response = orderService.retrieveOrderForm(productIds, memberInfo, authInfo);
    //
    //     then(givenCouponRepository).should(times(1)).findOwnCouponsByMemberId(anyLong());
    //     then(pointRepository).should(times(1)).findLastTotalPoints(anyLong());
    //     then(deliveryAddressRepository).should(times(1)).findDeliveryAddressesByMemberId(anyLong());
    //
    //     assertThat(response.getMemberName()).isEqualTo("김더미");
    // }

    @Test
    @DisplayName("주문 목록 조회")
    void testRetrieveOrderList() {
        OrderRetrieveResponse orderResponse = Dummy.getOrderRetrieveResponse();

        given(orderRepository.findOrderList(anyLong(), anyBoolean(), any(Pageable.class))).willReturn(Page.empty());

        orderService.retrieveOrderList(memberInfo, new DefaultPageRequest(1).getPageable());

        then(orderRepository).should(times(1))
                             .findOrderList(anyLong(), anyBoolean(), any(Pageable.class));
    }

    @Test
    @DisplayName("주문 상세 조회")
    void testRetrieveOrderDetail() {
        OrderDetailRetrieveResponse orderDetailResponse = Dummy.getDummyOrderDetailResponse();

        given(orderRepository.findOrderDetail(anyLong(), anyLong(), anyBoolean())).willReturn(orderDetailResponse);

        orderService.retrieveOrderDetail(1L, memberInfo);

        then(orderRepository).should(times(1))
                             .findOrderDetail(anyLong(), anyLong(), anyBoolean());
    }

    @Test
    @DisplayName("주문 상태 변경")
    void testUpdateStatus() {
        OrderUpdateStatusRequest orderUpdateStatusRequest = new OrderUpdateStatusRequest();
        ReflectionTestUtils.setField(orderUpdateStatusRequest, "status", "배송 대기");

        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));
        given(orderRepository.save(any(Order.class))).willReturn(order);

        orderService.updateStatus(1L, orderUpdateStatusRequest);

        then(orderRepository).should(times(1)).findById(anyLong());
        then(orderRepository).should(times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("주문 운송장 번호 발급 요청")
    void testCreateTrackingNo() throws JsonProcessingException {
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));
        given(memberRepository.findUuidByOrderId(anyLong())).willReturn(Dummy.DUMMY_UUID);
        given(authRepository.getMemberInfo(any(MemberInfoRequest.class))).willReturn(shopResult);
        given(deliveryRepository.createTrackingNo(any(OrderInfoRequestDto.class)))
                .willReturn(new ResponseEntity<>(HttpStatus.CREATED));

        orderService.createTrackingNo(1L);

        then(orderRepository).should(times(1)).findById(anyLong());
        then(memberRepository).should(times(1)).findUuidByOrderId(anyLong());
        then(authRepository).should(times(1)).getMemberInfo(any(MemberInfoRequest.class));
        then(deliveryRepository).should(times(1))
                                .createTrackingNo(any(OrderInfoRequestDto.class));
    }

    @Test
    @DisplayName("주문 취소")
    void testCancelOrder() {
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));
        given(orderRepository.save(any(Order.class))).willReturn(order);

        orderService.cancelOrder(1L);

        then(orderRepository).should(times(1)).findById(anyLong());
        then(orderRepository).should(times(1)).save(any(Order.class));
        then(publisher).should(times(1)).publishEvent(any(OrderPointCanceledEvent.class));
        then(publisher).should(times(1)).publishEvent(any(OrderCouponCanceledEvent.class));
    }

}
