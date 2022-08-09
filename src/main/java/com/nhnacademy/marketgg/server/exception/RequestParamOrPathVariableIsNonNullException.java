package com.nhnacademy.marketgg.server.exception;

public class RequestParamOrPathVariableIsNonNullException extends IllegalArgumentException {

    private static final String ERROR = "@PathVariable 과 @RequestParam 에는 Null 값이 들어올 수 없습니다.";

    public RequestParamOrPathVariableIsNonNullException() {
        super(ERROR);
    }

}
