package com.nhnacademy.marketgg.server.exception.auth;

public class UnAuthenticException extends IllegalAccessException {

    public UnAuthenticException() {
        super("로그인 후 접근 가능합니다.");
    }

}
