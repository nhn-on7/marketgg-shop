package com.nhnacademy.marketgg.server.dto.request.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 장바구니로부터 주문할 상품에 대한 정보를 전달하기 위한 DTO 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductToOrder {

    @Schema(title = "상품 번호", description = "주문할 상품의 식별번호입니다.", example = "1L")
    @NotNull
    private Long id;

    @Schema(title = "상품 이름", description = "주문할 상품의 이름입니다.", example = "어묵탕 키트")
    @NotBlank
    private String name;

    @Schema(title = "상품 가격", description = "주문할 상품의 가격입니다.", example = "10000L")
    @NotNull
    private Long price;

    @Schema(title = "상품 수량", description = "주문할 상품의 수량입니다.", example = "2")
    @NotNull
    private Integer amount;

    @Schema(title = "상품 썸네일",
            description = "주문할 상품의 썸네일입니다.",
            example = "https://api-storage.cloud.toast.com/v1/AUTH_8a2dd42738a0427180466a56561b5eef/on7_storage/2a01e362-b7b7-49e2-8e31-6ed5aa674761.jpg")
    private String imageAddress;

}
