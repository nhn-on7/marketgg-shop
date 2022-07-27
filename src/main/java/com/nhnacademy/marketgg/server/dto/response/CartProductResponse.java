package com.nhnacademy.marketgg.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CartProductResponse {

    private final Long id;
    private final String name;
    private final Integer amount;
    private final Long price;

}