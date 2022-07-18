package com.nhnacademy.marketgg.server.exception.auth;

public class UnAuthorizationException extends IllegalAccessException {
    public UnAuthorizationException(String methodName, String roles) {
        super("[ " + methodName + " ]: 접근 권한이 부족합니다. "
            + "소유 권한: " + roles);
    }
}
