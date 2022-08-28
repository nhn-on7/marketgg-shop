package com.nhnacademy.marketgg.server.dto.payment;

import com.nhnacademy.marketgg.server.dto.payment.result.CardPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.MobilePhonePaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.PaymentCancelResult;
import com.nhnacademy.marketgg.server.dto.payment.result.Receipt;
import com.nhnacademy.marketgg.server.dto.payment.result.TransferPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.VirtualAccountPaymentResult;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

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

    @Schema(title = "결제 처리 상태", description = "결제 처리 상태값을 가질 수 있습니다.", example = "DONE")
    @NotBlank
    @Size(max = 20)
    private String status;

    @Schema(title = "거래키", description = "결제 한 건에 대한 승인 및 취소 거래를 구분하는데 사용합니다.",
            example = "0A25AEB83EB828C7CBDB3E0C97834557")
    @NotBlank
    @Size(max = 64)
    private String transactionKey;

    @Schema(title = "결제 요청일자", description = "결제 요청이 일어난 날짜와 시간 정보입니다.", example = "2022-01-01T00:00:00+09:00")
    @NotNull
    private String requestedAt;

    @Schema(title = "결제 승인일자", description = "결제 승인이 일어난 날짜와 시간 정보입니다.", example = "2022-01-01T00:00:00+09:00")
    @NotNull
    private String approvedAt;

    @Schema(title = "카드", description = "카드로 결제하면 제공되는 카드 관련 정보입니다..")
    private CardPaymentResult card;

    private VirtualAccountPaymentResult virtualAccount;

    private TransferPaymentResult transfer;

    private MobilePhonePaymentResult mobilePhone;

    private String cashReceipt;

    private PaymentCancelResult cancels;

    @NotBlank
    @Size(max = 50)
    private String secret;

    @NotBlank
    @Size(min = 6, max = 8)
    private String type;

    private String easyPay;

    @NotBlank
    @Size(min = 2, max = 2)
    private String country;

    private String failure;

    @NotNull
    private Receipt receipt;

    @NotBlank
    @Size(min = 1, max = 3)
    private String currency;

    @Schema(title = "최종 결제 금액", description = "총 결제 금액입니다.", example = "75600")
    @NotNull
    private Long totalAmount;

    @Schema(title = "잔액", description = "취소할 수 있는 금액(잔고)입니다.", example = "100")
    @NotNull
    private Long balanceAmount;

    @NotNull
    private Long suppliedAmount;

    @NotNull
    private Long vat;

    @NotNull
    private Long taxFreeAmount;

    @Schema(title = "결제 수단", description = "결제할 때 사용한 결제 수단입니다.", example = "카드")
    @NotBlank
    @Size(min = 6, max = 21)
    private String method;

}
