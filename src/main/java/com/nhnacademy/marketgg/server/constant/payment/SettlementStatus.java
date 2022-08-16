package com.nhnacademy.marketgg.server.constant.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 정산 상태를 나타내는 상수 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public enum SettlementStatus {

    INCOMPLETED("정산 미완료"),
    COMPLETED("정산 완료");

    private final String name;

}
