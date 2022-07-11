package com.nhnacademy.marketgg.server.exception;

public class CartNotFoundException extends IllegalArgumentException {

    public CartNotFoundException(String ex) {
        super(ex);
    }

}
