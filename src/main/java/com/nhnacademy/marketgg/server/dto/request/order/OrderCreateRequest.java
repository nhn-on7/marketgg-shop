package com.nhnacademy.marketgg.server.dto.request.order;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문을 생성하기 위한 요청 정보를 담고 있는 DTO 클래스입니다.
 *
 * @author 김정민
 * @version 1.0.0
 */
@NoArgsConstructor
@Getter
public class OrderCreateRequest {

    @Schema(title = "회원 이름", description = "주문하는 회원의 이름입니다.", example = "김우식")
    @NotBlank
    private String name;

    @Schema(title = "회원 휴대폰 번호", description = "주문하는 회원의 휴대폰 번호입니다.", example = "010-3333-7800")
    @NotBlank
    private String phone;

    @Schema(title = "회원 이메일", description = "주문하는 회원의 이메일입니다.", example = "woosik@gmail.com")
    @NotBlank
    private String email;

    @Schema(title = "쿠폰 번호", description = "주문에 사용할 쿠폰 식별번호입니다.", example = "1L")
    private Long couponId;

    @Schema(title = "배송지 번호", description = "주문한 상품을 받을 회원의 배송지 식별번호입니다.", example = "1L")
    @NotNull
    private Long deliveryAddressId;

    @Schema(title = "주문 상품 번호", description = "주문할 상품의 식별번호 목록입니다.", example = "example = \"[\"1L\",\"2L\",\"3L\"]\"")
    @NotNull
    private List<Long> productIds;

    @Schema(title = "사용 포인트", description = "주문에 사용할 포인트 금액입니다.", example = "1000")
    @NotNull
    private Integer usedPoint;

    @Schema(title = "주문 상품 총액", description = "주문할 상품들의 총 금액입니다.", example = "30000")
    @NotNull
    private Long totalOrigin;

    @Schema(title = "최종 결제 금액", description = "할인 적용 후 실제 결제할 최종 금액입니다.", example = "26000")
    @NotNull
    private Long totalAmount;

    @Schema(title = "결제 수단", description = "회원이 선택한 결제 수단입니다.", example = "카드")
    @NotBlank
    private String paymentType;

    @Schema(title = "예상 적립포인트", description = "결제가 완료되고 적립될 포인트 예상 금액입니다.", example = "260")
    @NotNull
    private Integer expectedSavePoint;

}
