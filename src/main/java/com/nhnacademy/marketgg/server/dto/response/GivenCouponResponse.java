package com.nhnacademy.marketgg.server.dto.response;

import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class GivenCouponResponse {

    private final Long memberId;

    private final Long couponId;

    private final String couponName;

    private final String couponType;

    private final Integer couponMinimumMoney;

    private final Double couponDiscountAmount;

    private final LocalDateTime expirationPeriod;

    private final CouponState couponState;

    public GivenCouponResponse(GivenCoupon givenCoupon, CouponState state, LocalDateTime expirationPeriod) {
        this.memberId = givenCoupon.getMember().getId();
        this.couponId = givenCoupon.getPk().getCouponId();
        this.couponName = givenCoupon.getCoupon().getName();
        this.couponType = givenCoupon.getCoupon().getType();
        this.couponMinimumMoney = givenCoupon.getCoupon().getMinimumMoney();
        this.couponDiscountAmount = givenCoupon.getCoupon().getDiscountAmount();
        this.expirationPeriod = expirationPeriod;
        this.couponState = state;
    }

}
