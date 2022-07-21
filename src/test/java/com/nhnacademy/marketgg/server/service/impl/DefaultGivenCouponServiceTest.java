package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CouponRequest;
import com.nhnacademy.marketgg.server.dto.request.GivenCouponRequest;
import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.GivenCouponResponse;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultGivenCouponServiceTest {

    @InjectMocks
    DefaultGivenCouponService givenCouponService;

    @Mock
    GivenCouponRepository givenCouponRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    CouponRepository couponRepository;

    @Mock
    UsedCouponRepository usedCouponRepository;

    private static GivenCouponRequest givenCouponRequest;
    private static MemberCreateRequest memberCreateRequest;
    private static CouponRequest couponRequest;
    private static GivenCoupon givenCoupon;

    @BeforeAll
    static void beforeAll() {
        givenCouponRequest = new GivenCouponRequest();
        memberCreateRequest = new MemberCreateRequest();
        couponRequest = new CouponRequest();
        ReflectionTestUtils.setField(couponRequest, "expiredDate", 10);
        givenCoupon = new GivenCoupon(new Coupon(couponRequest), new Member(memberCreateRequest), new GivenCouponRequest());
    }

    @Test
    @DisplayName("지급 쿠폰 생성")
    void testCreateGivenCoupons() {
        given(memberRepository.findById(any())).willReturn(Optional.of(new Member(memberCreateRequest)));
        given(couponRepository.findById(any())).willReturn(Optional.of(new Coupon(couponRequest)));

        givenCouponService.createGivenCoupons(anyLong(), givenCouponRequest);

        then(givenCouponRepository).should().save(any(GivenCoupon.class));
    }

    @Test
    @DisplayName("지급 쿠폰 목록 전체 조회")
    void testRetrieveGivenCoupons() {
        given(givenCouponRepository.findAllByMemberId(any())).willReturn(List.of());

        givenCouponService.retrieveGivenCoupons(anyLong());

        verify(givenCouponRepository, atLeastOnce()).findAllByMemberId(any());
    }

    @Test
    @DisplayName("private method Test")
    void testCheckAvailability() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DefaultGivenCouponService defaultGivenCouponService = new DefaultGivenCouponService(givenCouponRepository, couponRepository, usedCouponRepository, memberRepository);

        Method method = defaultGivenCouponService.getClass().getDeclaredMethod("checkAvailability", GivenCoupon.class);
        method.setAccessible(true);

        Object invoke = method.invoke(defaultGivenCouponService, givenCoupon);
        verify(usedCouponRepository, atLeastOnce()).findAllByGivenCoupon(any());
        assertThat(invoke).isInstanceOf(GivenCouponResponse.class);
    }

}
