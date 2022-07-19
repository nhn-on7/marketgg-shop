package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.GivenCouponRequest;
import com.nhnacademy.marketgg.server.dto.response.GivenCouponResponse;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.GivenCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultGivenCouponService implements GivenCouponService {

    private final GivenCouponRepository givenCouponRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    @Override

    public void createGivenCoupons(final Long memberId,
                                   final GivenCouponRequest givenCouponRequest) {

        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(MemberNotFoundException::new);

        Coupon coupon = couponRepository.findById(givenCouponRequest.getCouponNo())
                                        .orElseThrow(CouponNotFoundException::new);

        GivenCoupon givenCoupon = new GivenCoupon(coupon, member, memberId, givenCouponRequest);

        givenCouponRepository.save(givenCoupon);
    }

    @Override
    public List<GivenCouponResponse> retrieveGivenCoupons(final Long memberId) {
        List<GivenCouponResponse> givenCoupons = givenCouponRepository.findAllByMemberNo(memberId);



        return null;
    }
}
