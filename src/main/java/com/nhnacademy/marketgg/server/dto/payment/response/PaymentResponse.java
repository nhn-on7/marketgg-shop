package com.nhnacademy.marketgg.server.dto.payment.response;

import com.nhnacademy.marketgg.server.dto.payment.result.CardPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.MobilePhonePaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.PaymentCancels;
import com.nhnacademy.marketgg.server.dto.payment.result.TransferPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.VirtualAccountPaymentResult;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
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

    @Schema(title = "주문번호", description = "마켓 GG 에서 관리하며, 앞에 GGORDER_ 로 구성된 주문번호입니다.", example = "GGORDER_21")
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

    @Schema(title = "카드 결제 정보", description = "카드 결제 시 제공되는 카드 관련 정보입니다.")
    @Nullable
    private CardPaymentResult card;

    @Schema(title = "가상계좌 결제 정보", description = "가상계좌 결제 시 제공되는 가상계좌 관련 정보입니다.")
    @Nullable
    private VirtualAccountPaymentResult virtualAccount;

    @Schema(title = "계좌이체 결제 정보", description = "계좌이체로 결제 시 제공되는 계좌이체 관련 정보입니다.")
    @Nullable
    private TransferPaymentResult transfer;

    @Schema(title = "휴대폰 결제 정보", description = "휴대폰 결제 시 제공되는 휴대폰 관련 정보입니다.")
    @Nullable
    private MobilePhonePaymentResult mobilePhone;

    @Schema(title = "결제 취소 이력", description = "결제 취소 이력이 담기는 배열입니다.")
    @Nullable
    private List<PaymentCancels> cancels;

    @Schema(title = "웹훅 시크릿 키", description = "가상계좌 웹훅 요청이 정상적인 요청인지 검증하기 위한 값입니다.",
            example = "ps_5GePWvyJnrK24lwpZyqVgLzN97Eo")
    @Nullable
    @Size(max = 50)
    private String secret;

    @Schema(title = "결제 타입", description = "결제 타입 정보로 일반 결제, 자동 결제, 브랜드페이 중 하나입니다.", example = "NORMAL")
    @NotBlank
    @Size(min = 6, max = 8)
    private String type;

    @Schema(title = "결제 국가", description = "ISO-3166 의 두 자리 국가 코드 형식의 결제한 국가 정보입니다.", example = "KR")
    @NotBlank
    @Size(min = 2, max = 2)
    private String country;

    @Schema(title = "결제 실패 정보", description = "결제 실패 정보입니다.")
    @Nullable
    private PaymentFailure failure;

    @Schema(title = "영수증 정보", description = "발행된 영수증 정보가 담긴 객체입니다.")
    @NotNull
    private Receipt receipt;

    @Schema(title = "결제 실패 정보", description = "결제 실패 정보입니다.")
    @Nullable
    private CashReceipt cashReceipt;

    @Schema(title = "통화 단위", description = "결제할 때 사용한 통화 단위입니다.", example = "KRW")
    @NotBlank
    @Size(min = 1, max = 3)
    private String currency;

    @Schema(title = "최종 결제 금액", description = "총 결제 금액입니다.", example = "75600")
    @NotNull
    private Long totalAmount;

    @Schema(title = "잔액", description = "취소할 수 있는 금액(잔고)입니다.", example = "200")
    @NotNull
    private Long balanceAmount;

    @Schema(title = "공급가액", description = "부가세가 포함되지 않은 물품가격입니다.", example = "182")
    @NotNull
    private Long suppliedAmount;

    @Schema(title = "부가세", description = "(결제 금액 - 면세 금액) / 11 후 소수점 첫째 자리에서 반올림한 부가세입니다.", example = "18")
    @NotNull
    private Long vat;

    @Schema(title = "면세 금액", description = "전체 결제 금액 중 면세 금액입니다. 값이 0으로 돌아왔다면 전체 결제 금액이 과세 대상입니다.")
    @NotNull
    private Long taxFreeAmount;

    @Schema(title = "결제 수단", description = "결제할 때 사용한 결제 수단입니다.", example = "카드")
    @NotBlank
    @Size(min = 6, max = 21)
    private String method;

}
