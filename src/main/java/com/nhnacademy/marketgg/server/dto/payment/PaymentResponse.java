package com.nhnacademy.marketgg.server.dto.payment;

import com.nhnacademy.marketgg.server.dto.payment.result.CardPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.MobilePhonePaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.PaymentCancelResult;
import com.nhnacademy.marketgg.server.dto.payment.result.Receipt;
import com.nhnacademy.marketgg.server.dto.payment.result.TransferPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.VirtualAccountPaymentResult;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 결제 요청에 대해 결제대행사로부터 전달받는 응답 데이터 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class PaymentResponse {

    @Schema(title = "결제키", description = "결제대행사에서 발급해주는 결제 키입니다.",
            example = "EAK6k75XwlOyL0qZ4G1VOP4xk47qOroWb2MQYgmBDPdR9pxz")
    @NotBlank
    @Size(max = 200)
    private String paymentKey;

    @Schema(title = "주문번호", description = "마켓 GG 에서 관리하며, 앞에 GGORDER_ 로 구성된 주문번호입니다.", example = "GGORDER_628")
    @NotBlank
    @Size(min = 6, max = 64)
    private String orderId;

    @Schema(title = "주문이름", description = "결제에 대한 주문 이름입니다.", example = "[프렙] 쉬림프 로제 리조또 외 3건")
    @NotBlank
    @Size(max = 100)
    private String orderName;

    @NotBlank
    @Size(max = 20)
    private String status;

    @NotBlank
    @Size(max = 64)
    private String transactionKey;

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

    @NotBlank
    @Max(50)
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
