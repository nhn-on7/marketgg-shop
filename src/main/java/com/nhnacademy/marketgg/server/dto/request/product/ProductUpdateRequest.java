package com.nhnacademy.marketgg.server.dto.request.product;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 상품 수정을 위한 DTO 입니다.
 *
 * @author 조현진
 */
@NoArgsConstructor
@Getter
public class ProductUpdateRequest {

    @Schema(title = "카테코리", description = "상품이 속한 카테고리", example = "001")
    @NotBlank(message = "카테고리는 null일수 없습니다.")
    private String categoryCode;

    @Schema(title = "라벨 번호", description = "상품이 보유한 라벨번호", example = "1")
    @NotNull(message = "라벨번호는 null일수 없습니다.")
    private Long labelNo;

    @Schema(title = "상품 이름", description = "상품 이름", example = "포카칩")
    @NotBlank(message = "상품명은 null일수 없습니다.")
    private String name;

    @Schema(title = "상품 간단 설명", description = "목록에 보이는 간단한 설명", example = "바삭한 감자과자")
    @NotBlank(message = "content는 null일수 없습니다.")
    private String content;

    @Schema(title = "재고", description = "상품의 남은 개수", example = "500")
    @NotNull(message = "재고는 null일수 없습니다.")
    private Long totalStock;

    @Schema(title = "가격", description = "상품 가격", example = "1200")
    @NotNull(message = "가격은 null일수 없습니다.")
    private Long price;

    @Schema(title = "설명", description = "상품 상세정보 클릭시 보이는 사진을 포함한 정보", example = "사진 + 맛있는 감자칩 블라블라")
    @NotBlank(message = "상품 설명은 null일수 없습니다.")
    private String description;

    @Schema(title = "단위", description = "상품 판매시 단위", example = "1 개")
    @NotBlank(message = "unit은 null일수 없습니다.")
    private String unit;

    @Schema(title = "배송 타입", description = "상품의 배송 종류", example = "일반, 냉장")
    @NotBlank(message = "배송 타입은 null일수 없습니다.")
    private String deliveryType;

    @Schema(title = "원산지", description = "상품의 원산지", example = "한국")
    @NotBlank(message = "원산지는 null일수 없습니다.")
    private String origin;

    @Schema(title = "포장", description = "상품 포장이 어떤가", example = "일반")
    @NotBlank(message = "포장 타입은 null일수 없습니다.")
    private String packageType;

    @Schema(title = "유통기한", description = "상품의 유통기한", example = "2022-09-16")
    @NotNull(message = "유통기한은 null일수 없습니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    @Schema(title = "알러지", description = "상품의 알러지 정보", example = "감자")
    @NotBlank(message = "알러지 정보는 null일수 없습니다.")
    private String allergyInfo;

    @Schema(title = "용량", description = "상품의 용량", example = "66g")
    @NotBlank(message = "capacity는 null일수 없습니다.")
    private String capacity;

}
