package com.nhnacademy.marketgg.server.constant.payment;

import java.util.Arrays;
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

    /**
     * 문자열로 구성된 카드 종류 구분을 통해 열거형을 추출합니다.
     *
     * @param cardType - 문자열로 구성된 카드 종류
     * @return 문자열로 받은 카드 종류 {@link CardType}
     */
    public static CardType of(String cardType) {
        return Arrays.stream(CardType.values())
                     .filter(v -> v.getName().equals(cardType))
                     .findAny()
                     .orElseThrow(IllegalArgumentException::new);
    }

}
