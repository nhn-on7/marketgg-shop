package com.nhnacademy.marketgg.server.exception;

public class ProductCreationFailedException extends RuntimeException{

    public ProductCreationFailedException(String message) {
        super(message);
    }
}
