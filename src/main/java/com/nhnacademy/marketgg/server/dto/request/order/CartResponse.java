package com.nhnacademy.marketgg.server.dto.request.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
public class CartResponse {

    @NotNull
    private List<ProductToOrder> products;

}
