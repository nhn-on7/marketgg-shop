package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class GivenCouponResponse {

    private final Long memberId;

    private final String couponName;

    private final String couponType;

    private final Integer couponExpiredDate;

    private final Integer couponMinimumMoney;

    private final Double couponDiscountAmount;

    private final LocalDateTime createdAt;

}
