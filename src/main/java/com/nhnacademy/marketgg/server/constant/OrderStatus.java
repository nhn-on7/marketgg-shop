package com.nhnacademy.marketgg.server.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {

    PAY_WAITING("결제대기"),
    PAY_COMPLETE("결제완료"),
    CANCEL_WAITING("취소/환불접수"),
    CANCEL_COMPLETE("취소/환불완료");

    private final String status;

}
