package com.nhnacademy.marketgg.server.exception.auth;

public class AuthServerResponseException extends RuntimeException {
    private static final String MESSAGE = "Auth 서버 통신중 에러 발생.";

    public AuthServerResponseException() {
        super(MESSAGE);
    }
}
