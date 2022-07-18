package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CouponRetrieveResponse {

    private final Long id;

    private final String name;

    private final String type;

    private final LocalDateTime expiredAt;

    private final Integer minimumMoney;

}
