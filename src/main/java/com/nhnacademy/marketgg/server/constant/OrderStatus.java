package com.nhnacademy.marketgg.server.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {

    DEPOSIT_WAITING("입금대기"),
    PAY_WAITING("결제대기"),
    PAY_COMPLETE("결제완료"),
    CANCEL_COMPLETE("취소/환불완료"),
    DELIVERY_WAITING("배송준비"),
    DELIVERY_SHIPPING("배송중"),
    DELIVERY_COMPLETE("배송완료");

    private final String status;

}
