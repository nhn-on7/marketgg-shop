package com.nhnacademy.marketgg.server.exception.member;

public class MemberNotFoundException extends IllegalArgumentException {

    public MemberNotFoundException(String msg) {
        super(msg);
    }

}
