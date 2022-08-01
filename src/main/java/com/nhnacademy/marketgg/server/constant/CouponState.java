package com.nhnacademy.marketgg.server.constant;

public enum CouponState {

    EXPIRED("기간만료"),
    USED("사용완료"),
    VALID("사용가능");

    private final String state;

    CouponState(String state) {
        this.state = state;
    }

    public String state() {
        return this.state;
    }

}
