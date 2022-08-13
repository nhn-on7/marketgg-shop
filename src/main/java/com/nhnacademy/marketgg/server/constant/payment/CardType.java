package com.nhnacademy.marketgg.server.constant.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 카드의 종류를 나타내는 상수 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public enum CardType {

    CREDIT("신용"),
    DEBIT("체크"),
    GIFT("기프트");

    private final String name;

}
