package com.nhnacademy.marketgg.server.exception;

public class CategoryNotFoundException extends IllegalArgumentException {

    public CategoryNotFoundException(String message) {
        super(message);
    }

}
