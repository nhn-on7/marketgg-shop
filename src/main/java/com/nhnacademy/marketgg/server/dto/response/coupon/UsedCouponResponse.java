package com.nhnacademy.marketgg.server.dto.response.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UsedCouponResponse {

    private final String name;

    private final Double discountAmount;

}
