package com.nhnacademy.marketgg.server.constant.payment;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 가상계좌 결제 타입을 나타내는 상수 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public enum AccountType {

    NORMAL("일반"),
    FIXED("고정");

    private final String name;

    /**
     * 문자열로 구성된 가상계좌 타입을 통해 열거형을 추출합니다.
     *
     * @param accountType - 문자열로 구성된 가상계좌 타입
     * @return 열거형 타입의 가상계좌 {@link AccountType}
     */
    public static AccountType of(String accountType) {
        return Arrays.stream(AccountType.values())
                     .filter(v -> v.getName().equals(accountType))
                     .findAny()
                     .orElseThrow(IllegalArgumentException::new);
    }

}
