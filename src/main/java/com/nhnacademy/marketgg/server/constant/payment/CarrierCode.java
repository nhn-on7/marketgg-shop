package com.nhnacademy.marketgg.server.constant.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 통신사 코드입니다.
 *
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
public enum CarrierCode {

    KT("KT"),
    LGU("LG 유플러스"),
    SKT("SK 텔레콤"),
    HELLO("LG 헬로모바일"),
    KCT("티플러스"),
    SK7("SK 세븐모바일");

    private final String carrierName;

}
