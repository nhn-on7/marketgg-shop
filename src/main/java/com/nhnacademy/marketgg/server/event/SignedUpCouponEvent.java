package com.nhnacademy.marketgg.server.event;

import static com.nhnacademy.marketgg.server.constant.CouponName.SIGNUP;

import com.nhnacademy.marketgg.server.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignedUpCouponEvent {

    private String couponName;

    private Member member;

    public static SignedUpCouponEvent signUpCoupon(Member member) {
        return new SignedUpCouponEvent(SIGNUP.couponName(), member);
    }

}
