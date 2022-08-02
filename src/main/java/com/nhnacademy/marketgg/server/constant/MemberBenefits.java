package com.nhnacademy.marketgg.server.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원 등급에 따른 포인트 적립률 상수입니다.
 *
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public enum MemberBenefits {

    VIP(3),
    G_VIP(5);

    private final int benefit;

}
