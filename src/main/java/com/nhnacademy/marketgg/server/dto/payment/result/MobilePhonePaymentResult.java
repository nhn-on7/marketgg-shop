package com.nhnacademy.marketgg.server.dto.payment.result;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 휴대폰 결제 시 제공되는 휴대폰 결제 관련 정보입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class MobilePhonePaymentResult {

    @NotBlank
    @Max(20)
    private String customerMobilePhone;

    @NotBlank
    @Size(min = 8, max = 20)
    private String settlementStatus;

    @NotBlank
    @Max(255)
    private String receiptUrl;

}
