package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 주문을 생성하기 위한 요청 정보를 담고 있는 DTO 클래스입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
public class OrderCreateRequest {

    private Long memberId;

    private Long couponId;

    private Long deliveryAddressId;

    // 상품번호, 수량
    private Map<Long, Long> productMap;

    private Integer usedPoint;

    private Long totalAmount;

    // 결제 방식
    private String paymentType;

}
