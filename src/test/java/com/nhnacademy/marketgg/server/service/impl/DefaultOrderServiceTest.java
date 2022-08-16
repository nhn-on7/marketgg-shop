package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.repository.deliveryaddress.DeliveryAddressRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.service.order.DefaultOrderService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
public class DefaultOrderServiceTest {

    @InjectMocks
    DefaultOrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    DeliveryAddressRepository deliveryAddressRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CouponRepository couponRepository;

    // FIXME: 주문 등록에 대한 테스트 코드 작성이 필요해보입니다. @김정민

    // @Test
    // @DisplayName("주문 등록")
    // void testCreateOrder() {
    //     Member member = Dummy.getDummyMember(new Cart());
    //     DeliveryAddress deliveryAddress = Dummy.getDummyDeliveryAddress();
    //     Product product = Dummy.getDummyProduct(1L);
    //     Coupon coupon = Dummy.getDummyCoupon();
    //
    //     given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
    //     given(deliveryAddressRepository.findById(anyLong())).willReturn(Optional.of(deliveryAddress));
    //     given(productRepository.findByIds(List.of(1L))).willReturn(List.of(product));
    //     given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));
    //
    // }

}
