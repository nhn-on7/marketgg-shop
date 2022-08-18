package com.nhnacademy.marketgg.server.dto.response.coupon;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class GivenCouponResponse {

    private final Long couponId;

    private final Long memberId;

    private final String name;

    private final String type;

    private final Integer minimumMoney;

    private final Double discountAmount;

    private final LocalDateTime expiredDate;

    private final String status;

}
