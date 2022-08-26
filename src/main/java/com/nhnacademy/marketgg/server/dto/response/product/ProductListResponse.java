package com.nhnacademy.marketgg.server.dto.response.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 리스트 조회 시 보여줄 필드를 모아둔 객체입니다.
 *
 * @version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductListResponse {

    @Schema(title = "상품 번호", description = "검색한 상품의 식별번호입니다.", example = "1")
    private Long id;

    @Schema(title = "카테고리 번호", description = "검색을 진행한 카테고리의 식별번호입니다.", example = "100")
    private String categoryCode;

    @Schema(title = "상품 이름", description = "검색한 상품의 이름입니다.", example = "계란")
    private String productName;

    @Schema(title = "상품 간단 설명", description = "검색한 상품의 간단 설명입니다.", example = "맛있습니다.")
    private String content;

    @Schema(title = "상품 상세 설명", description = "검색한 상품의 상세 설명입니다.", example = "<p>굉장히 맛이 있네요!<p>")
    private String description;

    @Schema(title = "라벨 이름", description = "검색한 상품의 라벨이름입니다.", example = "10% 할인")
    private String labelName;

    @Schema(title = "이미지 주소", description = "검색한 상품의 썸네일 이미지 주소입니다.", example = "something image url")
    private String imageAddress;

    @Schema(title = "가격", description = "검색한 상품의 가격입니다.", example = "1000")
    private Long price;

    @Schema(title = "수량", description = "검색한 상품의 남은 수량입니다.", example = "1000")
    private Long amount;

}
