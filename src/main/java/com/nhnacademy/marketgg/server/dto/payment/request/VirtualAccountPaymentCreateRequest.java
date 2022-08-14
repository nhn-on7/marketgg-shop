package com.nhnacademy.marketgg.server.dto.payment.request;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 가상계좌 결제에 대한 응답 결과 객체입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class VirtualAccountPaymentCreateRequest {

    @NotNull
    private String accountType;

    @NotBlank
    @Size(max = 20)
    private String accountNumber;

    @NotBlank
    @Size(max = 20)
    private String bank;

    @NotBlank
    @Size(max = 100)
    private String customerName;

    @NotBlank
    @Size(max = 20)
    private String dueDate;

    @NotNull
    private String refundStatus;

    @Column
    private String expired;

    @NotNull
    private String settlementStatus;

}
