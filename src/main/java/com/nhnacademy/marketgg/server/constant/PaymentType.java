package com.nhnacademy.marketgg.server.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentType {

    CARD("카드"),
    TRANSFER("계좌이체"),
    VIRTUAL("가상계좌"),
    PHONE("휴대폰");

    private final String type;

}
