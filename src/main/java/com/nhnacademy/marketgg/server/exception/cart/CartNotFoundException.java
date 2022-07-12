package com.nhnacademy.marketgg.server.exception.cart;

public class CartNotFoundException extends IllegalArgumentException {

    public CartNotFoundException(String msg) {
        super(msg);
    }

}
