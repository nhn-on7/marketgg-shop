package com.nhnacademy.marketgg.server.constant;

public enum CouponStatus {

    EXPIRED("기간만료"),
    USED("사용완료"),
    VALID("사용가능");

    private final String status;

    CouponStatus(String status) {
        this.status = status;
    }

    public String state() {
        return this.status;
    }

}
