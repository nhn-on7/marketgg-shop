package com.nhnacademy.marketgg.server.dto.payment.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 결제 요청을 검증하기 위한 요청 객체입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
@Getter
@ToString
public class PaymentRequest {

    @NotBlank
    @Size(min = 6, max = 64)
    private final String orderId;

    @NotBlank
    @Size(max = 200)
    private final String paymentKey;

    @NotNull
    private final Long amount;

}
