package com.nhnacademy.marketgg.server.entity.event;

import static com.nhnacademy.marketgg.server.constant.CouponName.BESTREVIEW;
import static com.nhnacademy.marketgg.server.constant.CouponName.SIGNUP;

import com.nhnacademy.marketgg.server.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GivenCouponEvent {

    private String couponName;

    private Member member;

    public static GivenCouponEvent signUpCoupon(Member member) {
        return new GivenCouponEvent(SIGNUP.couponName(), member);
    }

    public static GivenCouponEvent bestReviewCoupon(Member member) {
        return new GivenCouponEvent(BESTREVIEW.couponName(), member);
    }

}
