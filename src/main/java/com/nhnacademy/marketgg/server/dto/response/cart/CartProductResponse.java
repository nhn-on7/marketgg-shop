package com.nhnacademy.marketgg.server.dto.response.cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class CartProductResponse {

    private final Long id;
    private final String thumbnail;
    private final String name;
    private final Integer amount;
    private final Long price;

}
