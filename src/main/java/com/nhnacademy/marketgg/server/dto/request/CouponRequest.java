package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CouponRequest {

    private String name;

    private String type;

    private Integer expiredDate;

    private Integer minimumMoney;

    private Double discountAmount;

}
