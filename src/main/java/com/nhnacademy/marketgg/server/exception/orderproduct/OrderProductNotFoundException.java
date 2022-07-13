package com.nhnacademy.marketgg.server.exception.orderproduct;

public class OrderProductNotFoundException extends IllegalArgumentException {

    public OrderProductNotFoundException(String msg) {
        super(msg);
    }

}
