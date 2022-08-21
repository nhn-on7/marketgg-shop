package com.nhnacademy.marketgg.server.dto.payment.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 가상계좌 결제에 대한 응답 결과 객체입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public class VirtualAccountCreateRequest {

    @Schema(title = "총 결제금액", description = "적립금 및 쿠폰 내역이 반영된 최종 결제 금액입니다.", example = "75600")
    @NotNull
    private final Long amount;

    @Schema(title = "가상계좌 발급 은행", description = "가상계좌 발급 요청을 한 은행입니다.", example = "신한")
    @NotBlank
    @Size(max = 20)
    private final String bank;

    @Schema(title = "가상계좌 발급 고객 이름", description = "가상계좌를 발급한 회원(고객)의 이름입니다.", example = "강태풍")
    @NotBlank
    @Size(max = 100)
    private final String customerName;

    @Schema(title = "주문번호", description = "마켓 GG 에서 관리하며, 앞에 GGORDER_ 로 구성된 주문번호입니다.", example = "GGORDER_628")
    @NotBlank
    @Size(min = 6, max = 64)
    private final String orderId;

    @Schema(title = "주문이름", description = "결제에 대한 주문 이름입니다.", example = "[프렙] 쉬림프 로제 리조또 외 3건")
    @NotBlank
    @Size(max = 100)
    private final String orderName;

    @Schema(title = "가상계좌 유효 시간", description = "생성된 가상계좌로 입금할 수 있는 유효한 시간을 의미합니다.", example = "24")
    @NotNull
    private final Integer validHours;

}
