package com.nhnacademy.marketgg.server.constant.payment;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 카드의 소유자 타입을 정의한 상수 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public enum OwnerType {

    INDIVIDUAL("개인"),
    CORPORATION("법인");

    private final String name;

    /**
     * 문자열로 구성된 카드 소유자 구분을 통해 열거형을 추출합니다.
     *
     * @param ownerType - 문자열로 구성된 카드 소유자 구분
     * @return 문자열로 받은 카드 소유자의 {@link OwnerType}
     */
    public static OwnerType of(String ownerType) {
        return Arrays.stream(OwnerType.values())
                     .filter(v -> v.getName().equals(ownerType))
                     .findAny()
                     .orElseThrow(IllegalArgumentException::new);
    }

}
