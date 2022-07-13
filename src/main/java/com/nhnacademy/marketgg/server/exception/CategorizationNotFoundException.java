package com.nhnacademy.marketgg.server.exception;

public class CategorizationNotFoundException extends IllegalArgumentException {

    private static final String ERROR = "카테고리 분류를 찾을 수 없습니다.";

    public CategorizationNotFoundException() {
        super(ERROR);
    }

}
