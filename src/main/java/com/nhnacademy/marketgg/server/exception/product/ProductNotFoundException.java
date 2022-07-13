package com.nhnacademy.marketgg.server.exception.product;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String msg) {
        super(msg);
    }

}
