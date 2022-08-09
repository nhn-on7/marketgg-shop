package com.nhnacademy.marketgg.server.dto.response.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class OrderGivenCoupon {

    private final Long id;

    private final String name;

    private final LocalDateTime createdAt; // 쿠폰 지급일시

    private final Integer expiredDate; // 쿠폰 유효기간(일)

    private final Integer minimumMoney;

    private final Double discountAmount; // 할인량(실수: 정률할인, 정수: 정액할인)

}
