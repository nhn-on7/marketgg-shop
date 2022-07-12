package com.nhnacademy.marketgg.server.exception.order;

public class OrderNotFoundException extends IllegalArgumentException {

    public OrderNotFoundException(String msg) {
        super(msg);
    }

}
