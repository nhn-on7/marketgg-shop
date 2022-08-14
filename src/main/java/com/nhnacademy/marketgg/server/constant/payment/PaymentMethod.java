package com.nhnacademy.marketgg.server.constant.payment;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 결제할 때 사용한 결제 수단을 나타내는 상수 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public enum PaymentMethod {

    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    TRANSFER("계좌이체"),
    MOBILE_PHONE("휴대폰");

    private final String name;

    public static PaymentMethod of(String method) {
        return Arrays.stream(PaymentMethod.values())
                     .filter(v -> v.getName().equals(method))
                     .findAny()
                     .orElseThrow(IllegalArgumentException::new);
    }

}
