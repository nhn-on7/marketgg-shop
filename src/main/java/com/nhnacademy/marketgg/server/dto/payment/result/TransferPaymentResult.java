package com.nhnacademy.marketgg.server.dto.payment.result;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 계좌이체로 결제 시 제공되는 계좌이체 관련 정보입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class TransferPaymentResult {

    @NotBlank
    @Size(max = 20)
    private String bank;

    @NotBlank
    @Size(min = 8, max = 20)
    private String settlementStatus;

}
