package com.nhnacademy.marketgg.server.service.payment;

import com.nhnacademy.marketgg.server.constant.payment.PaymentMethod;
import com.nhnacademy.marketgg.server.constant.payment.PaymentStatus;
import com.nhnacademy.marketgg.server.dto.payment.MobilePhonePaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.PaymentResponse;
import com.nhnacademy.marketgg.server.dto.payment.TransferPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.VirtualAccountPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.request.CardPaymentResult;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentCancelRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentVerifyRequest;
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

    PaymentResponse verifyRequest(final PaymentVerifyRequest paymentRequest);

    PaymentResponse pay(final PaymentRequest paymentRequest);

    PaymentResponse cancelPayment(Long paymentKey, PaymentCancelRequest paymentRequest);

    /**
     * 결제대행사로부터 받아온 결제 정보를 통해 결제 엔티티를 생성합니다.
     *
     * @param response - 결제대행사로부터 받아온 결제 요청에 대한 응답 정보
     * @return 결제 개체
     */
    default Payment toEntity(final PaymentResponse response) {
        return Payment.builder()
                      .orderName(response.getOrderName())
                      // .order(new Order(new Member(new MemberCreateRequest(), new Cart()), new OrderCreateRequest()))
                      .paymentKey(response.getPaymentKey())
                      .method(PaymentMethod.of(response.getMethod()))
                      .totalAmount(response.getTotalAmount())
                      .balanceAmount(response.getBalanceAmount())
                      // FIXME
                      .discountAmount(1_000L)
                      .status(PaymentStatus.valueOf(response.getStatus()))
                      .transactionKey(response.getTransactionKey())
                      .createdAt(LocalDateTime.now())
                      .updatedAt(LocalDateTime.now())
                      .build();
    }

    default CardPayment toEntity(CardPaymentResult cardPaymentRequest) {
        return null;
    }

    default VirtualAccountPayment toEntity(VirtualAccountPaymentResult virtualAccountResult) {
        return null;
    }

    default TransferPayment toEntity(TransferPaymentResult transferResult) {
        return null;
    }

    default MobilePhonePayment toEntity(MobilePhonePaymentResult mobilePhoneResult) {
        return null;
    }

}
