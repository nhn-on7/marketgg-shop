package com.nhnacademy.marketgg.server.constant.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 카드 결제의 매입 상태를 나타내는 상수 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public enum AcquireStatus {

    READY("매입 대기"),
    REQUESTED("매입 요청됨"),
    COMPLETED("매입 완료"),
    CANCEL_REQUESTED("매입 취소 요청됨"),
    CANCELED("매입 취소 완료");

    private final String name;

}
