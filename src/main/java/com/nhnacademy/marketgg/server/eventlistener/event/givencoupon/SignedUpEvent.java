package com.nhnacademy.marketgg.server.eventlistener.event.givencoupon;

import com.nhnacademy.marketgg.server.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원가입 시 발행하는 이벤트 구현체 입니다.
 *
 * @author 민아영
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class SignedUpEvent extends GiveCouponEvent {

    private final String couponName;

    private final Member member;

}
