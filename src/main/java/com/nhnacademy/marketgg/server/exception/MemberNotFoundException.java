package com.nhnacademy.marketgg.server.exception;

public class MemberNotFoundException extends IllegalArgumentException {

    public MemberNotFoundException(String ex) {
        super(ex);
    }

}
