package com.nhnacademy.marketgg.server.dto.response.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 주문 등록 후 결제 요청에 필요한 정보를 전달하기 위한 DTO 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class OrderToPayment {

    private final String orderId; // prefix + orderId

    private final String orderName;

    private final String memberName;

    private final String memberEmail;

    private final Long totalAmount;

    private final Long couponId;

    private final Integer usedPoint;

    private final Integer expectedSavePoint;

}
