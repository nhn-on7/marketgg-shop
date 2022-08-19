package com.nhnacademy.marketgg.server.dto.request.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderInfoRequestDto {

    private final String receiverName;

    private final String receiverAddress;

    private final String receiverDetailAddress;

    private final String receiverPhone;

    private final String orderNo;

}
