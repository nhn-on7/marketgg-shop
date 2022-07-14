package com.nhnacademy.marketgg.server.exception;

public class DibNotFoundException extends IllegalArgumentException {

    private static final String ERROR = "찜을 찾을 수 없습니다.";

    public DibNotFoundException() {
        super(ERROR);
    }

}
