package com.nhnacademy.marketgg.server.exception.category;

public class CategoryNotFoundException extends IllegalArgumentException {

    public CategoryNotFoundException(String msg) {
        super(msg);
    }

}
