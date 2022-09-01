package com.nhnacademy.marketgg.server.dto.payment.result;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.lang.Nullable;

/**
 * 결제 취소 이력이 담기는 배열입니다.
 *
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
public class PaymentCancels {

    @NotNull
    private Long cancelAmount;

    @NotBlank
    @Size(max = 200)
    private String cancelReason;

    @NotNull
    private Long taxFreeAmount;

    @Nullable
    private Long taxAmount;

    @NotNull
    private Long refundableAmount;

    @Schema(title = "결제 취소 일시", description = "결제 취소가 일어난 날짜와 시간 정보입니다.", example = "2022-01-01T00:00:00+09:00")
    @NotNull
    private String canceledAt;

    @Schema(title = "거래키", description = "결제 한 건에 대한 승인 및 취소 거래를 구분하는데 사용합니다.",
            example = "0A25AEB83EB828C7CBDB3E0C97834557")
    @NotBlank
    @Size(max = 64)
    private String transactionKey;

}
