package com.nhnacademy.marketgg.server.exception;

public class PaymentNotFoundException extends IllegalArgumentException {

    public PaymentNotFoundException(String ex) {
        super(ex);
    }

}
