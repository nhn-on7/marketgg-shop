package com.nhnacademy.marketgg.server.dto.payment;

import com.nhnacademy.marketgg.server.dto.payment.result.CardPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.MobilePhonePaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.PaymentCancelResult;
import com.nhnacademy.marketgg.server.dto.payment.result.TransferPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.VirtualAccountPaymentResult;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

/**
 * 결제 요청에 대한 응답 객체입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Getter
public class PaymentResponse {

    @NotBlank
    @Size(max = 200)
    private String paymentKey;

    @NotBlank
    @Size(max = 20)
    private String status;

    @NotBlank
    @Size(max = 64)
    private String transactionKey;

    @NotBlank
    @Size(min = 6, max = 64)
    private String orderId;

    @NotBlank
    @Size(max = 100)
    private String orderName;

    @NotNull
    private String requestedAt;

    @NotNull
    private String approvedAt;

    private CardPaymentResult card;

    private VirtualAccountPaymentResult virtualAccount;

    private TransferPaymentResult transfer;

    private MobilePhonePaymentResult mobilePhone;

    private String cashReceipt;

    private PaymentCancelResult cancels;

    @NotNull
    private String secret;

    @NotBlank
    @Size(min = 6, max = 8)
    private String type;

    private String easyPay;

    @NotBlank
    @Size(min = 2, max = 2)
    private String country;

    private String failure;

    private boolean isPartialCancelable;

    @NotNull
    private Receipt receipt;

    @NotBlank
    @Size(min = 1, max = 3)
    private String currency;

    @NotNull
    private Long totalAmount;

    @NotNull
    private Long balanceAmount;

    @NotNull
    private Long suppliedAmount;

    @NotNull
    private Long vat;

    @NotNull
    private Long taxFreeAmount;

    @NotBlank
    @Size(min = 6, max = 21)
    private String method;

}
