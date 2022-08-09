package com.nhnacademy.marketgg.server.dto.response.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class OrderGivenCoupon {

    private final Long couponId;

    private final String couponName;

    private final LocalDateTime couponExpiredAt;

    private final Integer couponMinimumMoney;

    private final Double couponDiscountAmount; // 할인량

}
