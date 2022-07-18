package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CouponRetrieveResponse {

    private final Long id;

    private final String name;

    private final String type;

    private final Integer expiredDate;

    private final Integer minimumMoney;

    private final Double disCountAmount;

}
