package com.nhnacademy.marketgg.server.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CouponStatus {

    EXPIRED("기간만료"),
    USED("사용완료"),
    VALID("사용가능");

    private final String status;

}
