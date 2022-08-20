package com.nhnacademy.marketgg.server.eventlistener.event.givencoupon;

import com.nhnacademy.marketgg.server.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 베스트 리뷰 선정 시 발행하는 이벤트 구현체 입니다.
 *
 * @author 민아영
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class BestReviewedEvent extends GiveCouponEvent {

    private final String couponName;

    private final Member member;

}
