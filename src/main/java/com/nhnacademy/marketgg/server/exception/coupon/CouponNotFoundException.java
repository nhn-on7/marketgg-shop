package com.nhnacademy.marketgg.server.exception.coupon;

public class CouponNotFoundException extends IllegalArgumentException {

    public CouponNotFoundException(String msg) {
        super(msg);
    }

}
