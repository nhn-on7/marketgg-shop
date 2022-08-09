package com.nhnacademy.marketgg.server.dto.request.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductToOrder {

    private Long id;
    private String name;
    private Long price;
    private Integer amount;

}
