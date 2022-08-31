package com.nhnacademy.marketgg.server.dto.payment.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 회원이 가상계좌에 주문한 건에 대해 입금한 정보를 포함하고 있는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
@ToString
public class VirtualAccountDepositRequest {

    @Schema(title = "총 결제금액", description = "적립, 쿠폰 내역이 반영된 최종 결제 금액입니다.", example = "2022-01-01T00:00:00.000")
    @NotNull
    private final LocalDateTime createdAt;

    @Schema(title = "시크릿 값", example = "AQItPnF1QIJ_hQ1vt4BnI",
            description = "가상계좌 요청이 정상 요청인지 검증을 위한 값으로, 결제 승인 API 의 응답으로 돌아온 시크릿 값과 같으면 정상적인 요청입니다.")
    @NotBlank
    @Size(max = 50)
    private final String secret;

    @Schema(title = "입금 처리 상태", description = "회원이 가상계좌에 금액을 입금했는지 확인하기 위한 값입니다.", example = "DONE")
    @NotBlank
    @Size(max = 20)
    private final String status;

    @Schema(title = "거래키", description = "가상계좌 입금 완료 및 취소에 대한 거래를 특정하는 키입니다.",
            example = "9FF15E1A29D0E77C218F57262BFA4986")
    @NotBlank
    @Size(max = 64)
    private final String transactionKey;

    @Schema(title = "주문번호", description = "마켓 GG 에서 관리하며, 앞에 GGORDER_ 로 구성된 주문번호입니다.", example = "GGORDER_628")
    @NotBlank
    @Size(min = 6, max = 64)
    private final String orderId;

}
