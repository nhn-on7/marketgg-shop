package com.nhnacademy.marketgg.server.constant.payment;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 결제할 때 사용한 결제 수단을 나타내는 상수 클래스입니다.
 *
 * @author 김정민
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public enum PaymentType {

    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    TRANSFER("계좌이체"),
    MOBILE_PHONE("휴대폰");

    private final String name;

    /**
     * 문자열로 구성된 결제 수단 구분을 통해 열거형을 추출합니다.
     *
     * @param type - 문자열로 구성된 결제 수단 구분
     * @return 문자열로 받은 결제 수단의 {@link PaymentType}
     */
    public static PaymentType of(String type) {
        return Arrays.stream(PaymentType.values())
                     .filter(v -> v.getName().equals(type))
                     .findAny()
                     .orElseThrow(IllegalArgumentException::new);
    }

}
