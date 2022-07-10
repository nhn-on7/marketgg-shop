package com.nhnacademy.marketgg.server.exception;

public class UsedCouponNotFoundException extends IllegalArgumentException {

    public UsedCouponNotFoundException(String ex) {
        super(ex);
    }
}
