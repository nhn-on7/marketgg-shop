package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderCreateResponse {

    private final Long totalAmount;

    private final String orderType;

    private final boolean isDirectBuy;

}
