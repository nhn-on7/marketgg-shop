package com.nhnacademy.marketgg.server.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.coupon.CouponDto;
import com.nhnacademy.marketgg.server.dto.request.coupon.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberCreateRequest;
import com.nhnacademy.marketgg.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.repository.cart.CartRepository;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import com.nhnacademy.marketgg.server.service.coupon.DefaultGivenCouponService;
import java.time.LocalDateTime;
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

    CouponDto couponDto;
    Coupon coupon;
    GivenCoupon givenCoupon;
    Cart cart;
    GivenCouponCreateRequest givenCouponRequest;
    MemberCreateRequest memberCreateRequest;
    Member member;

    Pageable pageable = PageRequest.of(0, 20);
    Page<GivenCoupon> inquiryPosts = new PageImpl<>(List.of(), pageable, 0);
    MemberInfo memberInfo;

    @BeforeEach
    void beforeEach() {
        givenCouponRequest = new GivenCouponCreateRequest();
        memberCreateRequest = new MemberCreateRequest();
        member = new Member(memberCreateRequest, new Cart());

        ReflectionTestUtils.setField(member, "id", 1L);
        ReflectionTestUtils.setField(givenCouponRequest, "name", "name");

        cart = cartRepository.save(new Cart());
        couponDto
            = new CouponDto(1L, "신규쿠폰", "정률할인", 1, 1, 0.5, false);
        coupon
            = new Coupon(1L, "신규쿠폰", "정률할인", 1, 1, 0.5, false, LocalDateTime.now());
        memberInfo = Dummy.getDummyMemberInfo(1L, cart);
    }

    @Test
    @DisplayName("지급 쿠폰 생성")
    void testCreateGivenCoupons() {
        given(memberRepository.findById(any())).willReturn(Optional.of(new Member(memberCreateRequest, cart)));
        given(memberRepository.findById(any())).willReturn(Optional.of(member));
        given(couponRepository.findCouponByName(anyString())).willReturn(Optional.of(coupon));

        givenCouponService.createGivenCoupons(memberInfo, givenCouponRequest);

        then(givenCouponRepository).should(times(1)).save(any(GivenCoupon.class));
    }

    @Test
    @DisplayName("지급 쿠폰 목록 전체 조회")
    void testRetrieveGivenCoupons() {
        given(givenCouponRepository.findByMemberIdOrderByCreatedAtDesc(anyLong(), any(Pageable.class))).willReturn(
            Optional.of(inquiryPosts));

        givenCouponService.retrieveGivenCoupons(memberInfo, pageable);

        then(givenCouponRepository).should(times(1)).findByMemberIdOrderByCreatedAtDesc(any(), any(Pageable.class));
    }

}
