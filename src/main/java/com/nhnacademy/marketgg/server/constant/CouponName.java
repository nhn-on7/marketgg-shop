package com.nhnacademy.marketgg.server.constant;

public enum CouponName {

    SIGNUP("신규 가입 쿠폰"),
    GVIP("GVIP 쿠폰"),
    VIP("VIP 쿠폰"),
    BESTREVIEW("베스트 후기 쿠폰"),
    BIRTHDAY("생일 쿠폰");

    private final String name;

    CouponName(String name) {
        this.name = name;
    }

    public String couponName() {
        return this.name;
    }

}
