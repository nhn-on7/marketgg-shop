package com.nhnacademy.marketgg.server.dto.payment.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

/**
 * 결제 승인 요청을 처리하기 위한 요청 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
@ToString
public class PaymentConfirmRequest {

    @Schema(title = "주문번호", description = "마켓 GG 에서 관리하며, 앞에 GGORDER_ 로 구성된 주문번호입니다.", example = "GGORDER_628")
    @NotBlank
    @Size(min = 6, max = 64)
    private final String orderId;

    @Schema(title = "결제키", description = "결제대행사에서 발급해주는 결제 키입니다.",
            example = "EAK6k75XwlOyL0qZ4G1VOP4xk47qOroWb2MQYgmBDPdR9pxz")
    @NotBlank
    @Size(max = 200)
    private final String paymentKey;

    @Schema(title = "총 결제금액", description = "적립금 및 쿠폰 내역이 반영된 최종 결제 금액입니다.", example = "75600")
    @NotNull
    private final Long amount;

    @Schema(title = "사용한 쿠폰 ID", description = "주문서 작성에서 사용한 쿠폰 ID 입니다.", example = "1L")
    private Optional<Long> couponId;

    @Schema(title = "사용한 포인트", description = "주문서 작성에서 사용한 포인트 금액입니다.", example = "100")
    @NotNull
    private Integer usedPoint;

    @Schema(title = "주문한 상품 ID 목록", description = "장바구니에서 주문한 상품 ID 목록입니다.", example = "1L, 2L")
    @NotNull
    private List<Long> orderedProducts;

}
