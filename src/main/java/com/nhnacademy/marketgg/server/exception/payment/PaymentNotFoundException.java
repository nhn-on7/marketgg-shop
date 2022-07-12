package com.nhnacademy.marketgg.server.exception.payment;

public class PaymentNotFoundException extends IllegalArgumentException {

    public PaymentNotFoundException(String msg) {
        super(msg);
    }

}
