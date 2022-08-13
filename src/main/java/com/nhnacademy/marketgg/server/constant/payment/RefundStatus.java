package com.nhnacademy.marketgg.server.constant.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 환불처리 상태를 나타내는 상수 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public enum RefundStatus {

    NONE("해당 없음"),
    FAILED("환불 실패"),
    PENDING("환불 처리중"),
    PARTIAL_FAILED("부분환불 실패"),
    COMPLETED("환불 완료");

    private final String name;

}
