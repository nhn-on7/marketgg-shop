package com.nhnacademy.marketgg.server.dto.payment.result;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 가상계좌로 결제 시 제공되는 가상계좌 관련 정보입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class VirtualAccountPaymentResult {

    @NotNull
    @Size(min = 2, max = 6)
    private String accountType;

    @NotBlank
    @Max(20)
    private String accountNumber;

    @NotBlank
    @Max(20)
    private String bank;

    @NotBlank
    @Max(100)
    private String customerName;

    @NotBlank
    // @Size(min = 3, max = 21)
    private String dueDate;

    @NotBlank
    @Size(min = 4, max = 20)
    private String refundStatus;

    private boolean expired;

    @NotBlank
    @Size(min = 8, max = 20)
    private String settlementStatus;

}
