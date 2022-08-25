package com.nhnacademy.marketgg.server.dto.request.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
public class CartResponse {

    @Schema(title = "주문할 상품 번호", description = "장바구니에서 선택한 주문할 상품 번호 목록입니다.",
            example = "[\"1L\",\"2L\",\"3L\"]")
    @NotNull
    private List<Long> productIds;

}
