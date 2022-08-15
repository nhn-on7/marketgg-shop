package com.nhnacademy.marketgg.server.service.payment;

import com.nhnacademy.marketgg.server.constant.payment.AcquireStatus;
import com.nhnacademy.marketgg.server.constant.payment.AgencyCode;
import com.nhnacademy.marketgg.server.constant.payment.CardType;
import com.nhnacademy.marketgg.server.constant.payment.OwnerType;
import com.nhnacademy.marketgg.server.dto.payment.request.CardPaymentResult;
import com.nhnacademy.marketgg.server.entity.payment.CardPayment;

/**
 * 카드 결제에 대한 비즈니스 처리를 담당합니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
public interface CardPaymentService extends PaymentService {

    @Override
    default CardPayment toEntity(CardPaymentResult cardPaymentRequest) {
        return CardPayment.builder()
                          .amount(cardPaymentRequest.getAmount())
                          .companyCode(AgencyCode.valueOf(cardPaymentRequest.getCompanyCode()))
                          .number(cardPaymentRequest.getNumber())
                          .cardType(CardType.valueOf(cardPaymentRequest.getCardType()))
                          .ownerType(OwnerType.valueOf(cardPaymentRequest.getOwnerType()))
                          .receiptUrl(cardPaymentRequest.getReceiptUrl())
                          .acquireStatus(AcquireStatus.valueOf(cardPaymentRequest.getAcquireStatus()))
                          .build();
    }

}
