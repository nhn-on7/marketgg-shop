package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.GivenCouponRequest;
import com.nhnacademy.marketgg.server.dto.response.CouponState;
import com.nhnacademy.marketgg.server.dto.response.GivenCouponResponse;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import com.nhnacademy.marketgg.server.service.GivenCouponService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.nhnacademy.marketgg.server.dto.response.CouponState.*;

@Service
@RequiredArgsConstructor
public class DefaultGivenCouponService implements GivenCouponService {

    private final GivenCouponRepository givenCouponRepository;
    private final CouponRepository couponRepository;
    private final UsedCouponRepository usedCouponRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void createGivenCoupons(final Long memberId,
                                   final GivenCouponRequest givenCouponRequest) {

        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(MemberNotFoundException::new);
        Coupon coupon = couponRepository.findById(givenCouponRequest.getCouponNo())
                                        .orElseThrow(CouponNotFoundException::new);

        GivenCoupon givenCoupon = new GivenCoupon(coupon, member, givenCouponRequest);
        givenCouponRepository.save(givenCoupon);
    }

    @Override
    public List<GivenCouponResponse> retrieveGivenCoupons(final Long memberId) {
        List<GivenCoupon> givenCoupons = givenCouponRepository.findAllByMemberId(memberId);
        List<GivenCouponResponse> couponResponseList = new ArrayList<>();
        for (GivenCoupon coupon : givenCoupons) {
            couponResponseList.add(this.checkAvailability(coupon));
        }
        return couponResponseList;
    }

    private GivenCouponResponse checkAvailability(GivenCoupon givenCoupon) {

        CouponState state;
        Integer expiredDate = givenCoupon.getCoupon().getExpiredDate();
        LocalDateTime expirationPeriod = givenCoupon.getCreatedAt().plusDays(expiredDate);

        if (!usedCouponRepository.findAllByGivenCoupon(givenCoupon).isEmpty()) {
            state = USED;
        } else if (expirationPeriod.isBefore(LocalDateTime.now())) {
            state = EXPIRED;
        } else {
            state = VALID;
        }
        return new GivenCouponResponse(givenCoupon, state, expirationPeriod);
    }

}
