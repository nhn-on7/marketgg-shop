package com.nhnacademy.marketgg.server.dto.request.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class ProductToOrder {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Long price;

    @NotNull
    private Integer amount;

}
