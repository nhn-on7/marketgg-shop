package com.nhnacademy.marketgg.server.dto.payment.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 현금영수증 정보입니다.
 *
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
public class CashReceipt {

    @NotBlank
    @Size(max = 20)
    private String type;

    @NotNull
    private Long number;

    @NotNull
    private Long taxFreeAmount;

    @NotBlank
    @Size(max = 30)
    private String issueNumber;

    @NotBlank
    @Size(max = 255)
    private String receiptUrl;

}
