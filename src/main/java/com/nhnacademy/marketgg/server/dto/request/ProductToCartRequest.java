package com.nhnacademy.marketgg.server.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductToCartRequest {

    @NotNull
    private Long id;

    @NotNull
    @Max(value = 999) @Min(value = 0)
    private Integer amount;

}
