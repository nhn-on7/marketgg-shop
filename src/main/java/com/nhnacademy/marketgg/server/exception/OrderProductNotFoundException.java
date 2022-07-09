package com.nhnacademy.marketgg.server.exception;

public class OrderProductNotFoundException extends IllegalArgumentException {

    public OrderProductNotFoundException(String ex) {
        super(ex);
    }

}
