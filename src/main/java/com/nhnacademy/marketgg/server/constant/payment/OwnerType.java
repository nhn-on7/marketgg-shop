package com.nhnacademy.marketgg.server.constant.payment;

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

}
