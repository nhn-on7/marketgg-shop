package com.nhnacademy.marketgg.server.dto.response.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * 주문서 작성 시 회원이 보유한 쿠폰 정보를 전달하기 위한 DTO 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class OrderGivenCoupon {

    private final Long id;

    private final String name;

    private final LocalDateTime createdAt; // 쿠폰 지급일시

    private final Integer expiredDate; // 쿠폰 유효기간(일)

    private final Integer minimumMoney;

    private final Double discountAmount; // 할인량(실수: 정률할인, 정수: 정액할인)

}
