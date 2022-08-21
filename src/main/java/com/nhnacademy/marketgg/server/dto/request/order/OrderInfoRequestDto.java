package com.nhnacademy.marketgg.server.dto.request.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 배송 서버에 주문 정보를 보내기 위한 DTO 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class OrderInfoRequestDto {

    private final String receiverName;

    private final String receiverAddress;

    private final String receiverDetailAddress;

    private final String receiverPhone;

    private final String orderNo;

}
