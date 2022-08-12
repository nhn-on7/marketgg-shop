package com.nhnacademy.marketgg.server.dto.info;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderInfoRequestDto {

    private final String receiverName;

    private final String receiverAddress;

    private final String receiverPhoneNumber;

    private final String orderNo;

}
