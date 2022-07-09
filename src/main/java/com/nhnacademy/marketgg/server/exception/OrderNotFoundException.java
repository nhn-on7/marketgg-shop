package com.nhnacademy.marketgg.server.exception;

public class OrderNotFoundException extends IllegalArgumentException {

    public OrderNotFoundException(String ex) {
        super(ex);
    }
}
