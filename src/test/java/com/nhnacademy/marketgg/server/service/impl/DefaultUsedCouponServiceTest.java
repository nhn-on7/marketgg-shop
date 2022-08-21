package com.nhnacademy.marketgg.server.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.server.dto.request.coupon.UsedCouponDto;
import com.nhnacademy.marketgg.server.dto.request.member.MemberCreateRequest;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import com.nhnacademy.marketgg.server.service.coupon.DefaultUsedCouponService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultUsedCouponServiceTest {

    @InjectMocks
    DefaultUsedCouponService usedCouponService;

    @Mock
    UsedCouponRepository usedCouponRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    GivenCouponRepository givenCouponRepository;

    UsedCouponDto usedCouponDto;
    Order order;
    GivenCoupon givenCoupon;
    UsedCoupon usedCoupon;
    Coupon coupon;
    MemberCreateRequest memberRequest;
    Member member;

    @BeforeEach
    void setUp() {
        order = Order.test();
        usedCouponDto = new UsedCouponDto();
        ReflectionTestUtils.setField(usedCouponDto, "orderId", 1L);
        ReflectionTestUtils.setField(usedCouponDto, "couponId", 1L);
        ReflectionTestUtils.setField(usedCouponDto, "memberId", 1L);

        memberRequest = new MemberCreateRequest();
        member = new Member(memberRequest, new Cart());
        coupon
            = new Coupon(1L, "신규쿠폰", "정률할인", 1, 1, 0.5);
        givenCoupon
            = new GivenCoupon(new GivenCoupon.Pk(1L, 1L), coupon, member, LocalDateTime.now());

        usedCoupon = new UsedCoupon(new UsedCoupon.Pk(1L, 1L, 1L), order, givenCoupon);
    }


    @Test
    void testCreateUsedCouponsSuccess() {
        given(orderRepository.findById(any())).willReturn(Optional.ofNullable(order));
        given(givenCouponRepository.findById(any())).willReturn(Optional.ofNullable(givenCoupon));

        usedCouponService.createUsedCoupons(usedCouponDto);

        then(usedCouponRepository).should(times(1)).save(any(UsedCoupon.class));
    }

    @Test
    void deleteUsedCoupons() {
        given(usedCouponRepository.findById(any())).willReturn(Optional.ofNullable(usedCoupon));

        usedCouponService.deleteUsedCoupons(usedCouponDto);

        then(usedCouponRepository).should(times(1)).delete(any(UsedCoupon.class));
    }

}
