package com.nhnacademy.marketgg.server.eventlistener.event.givencoupon;

import com.nhnacademy.marketgg.server.entity.Member;

/**
 * 회원가입 시 발행하는 이벤트 구현체 입니다.
 *
 * @author 민아영
 * @since 1.0.0
 */
public class SignedUpEvent extends GiveCouponEvent {

    public SignedUpEvent(String couponName, Member signUpMember) {
        super(couponName, signUpMember);
    }

}
