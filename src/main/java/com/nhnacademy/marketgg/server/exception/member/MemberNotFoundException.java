package com.nhnacademy.marketgg.server.exception.member;

public class MemberNotFoundException extends IllegalArgumentException {

    private static final String ERROR = "회원을 찾을 수 없습니다.";

    public MemberNotFoundException() {
        super(ERROR);
    }
}
