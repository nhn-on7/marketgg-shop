package com.nhnacademy.marketgg.server.dto.response.orderproduct;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderProductResponse {

    private final Long productId;

    private final String name;

    private final Long price;

    private final Integer amount;

}
