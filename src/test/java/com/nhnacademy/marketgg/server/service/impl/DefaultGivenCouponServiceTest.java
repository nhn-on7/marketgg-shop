package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.CouponDto;
import com.nhnacademy.marketgg.server.dto.request.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.GivenCouponResponse;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.repository.cart.CartRepository;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

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

    @Mock
    CartRepository cartRepository;

    private static GivenCouponCreateRequest givenCouponRequest;
    private static MemberCreateRequest memberCreateRequest;
    private static CouponDto couponDto;
    private static GivenCoupon givenCoupon;
    private static Cart cart;

    Pageable pageable = PageRequest.of(0, 20);
    Page<GivenCoupon> inquiryPosts = new PageImpl<>(List.of(), pageable, 0);

    @BeforeEach
    void beforeEach() {
        givenCouponRequest = new GivenCouponCreateRequest();
        memberCreateRequest = new MemberCreateRequest();
        Member member = new Member(memberCreateRequest, new Cart());

        ReflectionTestUtils.setField(member, "id", 1L);
        ReflectionTestUtils.setField(givenCouponRequest, "name", "name");

        couponDto = new CouponDto(1L, "name", "type", 10, 1000, 0.5);
        ReflectionTestUtils.setField(couponDto, "expiredDate", 10);
        cart = cartRepository.save(new Cart());
        givenCoupon = new GivenCoupon(new Coupon(1L, "name", "type", 10, 1000, 0.5),
                new Member(memberCreateRequest, cart));
    }

    @Test
    @DisplayName("지급 쿠폰 생성")
    void testCreateGivenCoupons() {
        given(memberRepository.findById(any())).willReturn(Optional.of(new Member(memberCreateRequest, cart)));
        given(couponRepository.findById(any())).willReturn(Optional.of(new Coupon(1L, "name", "type", 10, 1000, 0.5)));

        givenCouponService.createGivenCoupons(any(MemberInfo.class), givenCouponRequest);

        then(givenCouponRepository).should().save(any(GivenCoupon.class));
    }

    @Test
    @DisplayName("지급 쿠폰 목록 전체 조회")
    void testRetrieveGivenCoupons() {
        given(givenCouponRepository.findByMemberId(anyLong(), any(Pageable.class))).willReturn(Optional.of(inquiryPosts));

        givenCouponService.retrieveGivenCoupons(any(MemberInfo.class), pageable);

        verify(givenCouponRepository, atLeastOnce()).findByMemberId(any(), any(Pageable.class));
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
