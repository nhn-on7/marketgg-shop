package com.nhnacademy.marketgg.server.dto.response.coupon;

import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class GivenCouponResponse {

    private final Long couponId;

    private final Long memberId;

    private final String name;

    private final String type;

    private final Integer minimumMoney;

    private final Double discountAmount;

    private final LocalDateTime expiredDate;

    private final String state;

    public GivenCouponResponse(final GivenCoupon givenCoupon,
                               final String state,
                               final LocalDateTime expiredDate) {

        this.memberId = givenCoupon.getMember().getId();
        this.couponId = givenCoupon.getPk().getCouponId();
        this.name = givenCoupon.getCoupon().getName();
        this.type = givenCoupon.getCoupon().getType();
        this.minimumMoney = givenCoupon.getCoupon().getMinimumMoney();
        this.discountAmount = givenCoupon.getCoupon().getDiscountAmount();
        this.expiredDate = expiredDate;
        this.state = state;
    }

}
