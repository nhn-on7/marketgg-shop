package com.nhnacademy.marketgg.server.service.payment;

import com.nhnacademy.marketgg.server.constant.payment.AccountType;
import com.nhnacademy.marketgg.server.constant.payment.AcquireStatus;
import com.nhnacademy.marketgg.server.constant.payment.AgencyCode;
import com.nhnacademy.marketgg.server.constant.payment.BankCode;
import com.nhnacademy.marketgg.server.constant.payment.CardType;
import com.nhnacademy.marketgg.server.constant.payment.OwnerType;
import com.nhnacademy.marketgg.server.constant.payment.PaymentMethod;
import com.nhnacademy.marketgg.server.constant.payment.PaymentStatus;
import com.nhnacademy.marketgg.server.constant.payment.RefundStatus;
import com.nhnacademy.marketgg.server.constant.payment.SettlementStatus;
import com.nhnacademy.marketgg.server.dto.payment.PaymentResponse;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentCancelRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentVerifyRequest;
import com.nhnacademy.marketgg.server.dto.payment.result.CardPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.MobilePhonePaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.TransferPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.result.VirtualAccountPaymentResult;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.payment.CardPayment;
import com.nhnacademy.marketgg.server.entity.payment.MobilePhonePayment;
import com.nhnacademy.marketgg.server.entity.payment.Payment;
import com.nhnacademy.marketgg.server.entity.payment.TransferPayment;
import com.nhnacademy.marketgg.server.entity.payment.VirtualAccountPayment;
import java.time.LocalDateTime;

/**
 * 결제와 관련된 비즈니스 처리를 담당합니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
public interface PaymentService {

    /**
     * 회원의 결제 요청에 대한 검증을 처리합니다.
     *
     * @param paymentRequest - 결제 요청 정보
     * @return 성공 여부 응답 결과 반환
     */
    PaymentResponse verifyRequest(final PaymentVerifyRequest paymentRequest);

    /**
     * 최종 결제 승인을 처리합니다.
     *
     * @param paymentRequest - 결제 요청 정보
     */
    void pay(final PaymentRequest paymentRequest);

    /**
     * 승인된 결제를 취소합니다.
     *
     * @param paymentKey     - 결제 건에 대한 고유 키 값
     * @param paymentRequest - 결제 취소 요청 정보
     */
    void cancelPayment(final String paymentKey, final PaymentCancelRequest paymentRequest);

    /**
     * 결제대행사로부터 받아온 결제 정보를 통해 결제 엔티티를 생성합니다.
     *
     * @param order    - 주문 개체
     * @param response - 결제대행사로부터 받아온 결제 요청에 대한 응답 정보
     * @return 결제 개체
     */
    default Payment toEntity(final Order order, final PaymentResponse response) {
        return Payment.builder()
                      .orderName(response.getOrderName())
                      .order(order)
                      .paymentKey(response.getPaymentKey())
                      .method(PaymentMethod.of(response.getMethod()))
                      .totalAmount(response.getTotalAmount())
                      .balanceAmount(response.getBalanceAmount())
                      // FIXME: 할인금액 연동
                      .discountAmount(1_000L)
                      .status(PaymentStatus.valueOf(response.getStatus()))
                      .transactionKey(response.getTransactionKey())
                      .createdAt(LocalDateTime.now())
                      .updatedAt(LocalDateTime.now())
                      .build();
    }

    /**
     * 결제대행사로부터 제공받은 카드 결제 정보를 바탕으로 개체를 생성합니다.
     *
     * @param payment            - 결제 개체
     * @param cardPaymentRequest - 카드 결제 생성 정보
     * @return 카드 결제 개체
     */
    default CardPayment toEntity(final Payment payment, final CardPaymentResult cardPaymentRequest) {
        return CardPayment.builder()
                          .payment(payment)
                          .amount(cardPaymentRequest.getAmount())
                          .companyCode(AgencyCode.of(cardPaymentRequest.getCompanyCode()))
                          .number(cardPaymentRequest.getNumber())
                          .installmentPlanMonths(0)
                          .cardType(CardType.of(cardPaymentRequest.getCardType()))
                          .ownerType(OwnerType.of(cardPaymentRequest.getOwnerType()))
                          .receiptUrl(cardPaymentRequest.getReceiptUrl())
                          .acquireStatus(AcquireStatus.valueOf(cardPaymentRequest.getAcquireStatus()))
                          .build();
    }

    /**
     * 결제대행사로부터 제공받은 가상계좌 결제 정보를 바탕으로 개체를 생성합니다.
     *
     * @param payment - 결제 개체
     * @param result  - 가상계좌 결제 생성 정보
     * @return 가상계좌 개체
     */
    default VirtualAccountPayment toEntity(final Payment payment, final VirtualAccountPaymentResult result) {
        return VirtualAccountPayment.builder()
                                    .payment(payment)
                                    .accountType(AccountType.of(result.getAccountType()))
                                    .accountNumber(result.getAccountNumber())
                                    .bank(BankCode.of(result.getBank()))
                                    .customerName(result.getCustomerName())
                                    .dueDate(result.getDueDate())
                                    .refundStatus(RefundStatus.valueOf(result.getRefundStatus()))
                                    .expired(result.isExpired())
                                    .settlementStatus(SettlementStatus.valueOf(result.getSettlementStatus()))
                                    .build();
    }

    default TransferPayment toEntity(final TransferPaymentResult transferResult) {
        return null;
    }

    default MobilePhonePayment toEntity(final MobilePhonePaymentResult mobilePhoneResult) {
        return null;
    }

}
