package com.nhnacademy.marketgg.server.exception;

public class CategoryNotFoundException extends IllegalArgumentException {

    private static final String ERROR = "카테고리를 찾을 수 없습니다.";

    public CategoryNotFoundException() {
        super(ERROR);
    }

}
