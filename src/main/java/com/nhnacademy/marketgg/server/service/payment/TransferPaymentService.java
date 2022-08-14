package com.nhnacademy.marketgg.server.service.payment;

import com.nhnacademy.marketgg.server.dto.payment.TransferPaymentResult;
import com.nhnacademy.marketgg.server.entity.payment.TransferPayment;

/**
 * 계좌이체 결제와 관련된 비즈니스 처리를 담당합니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
public interface TransferPaymentService extends PaymentService {

    @Override
    default TransferPayment toEntity(TransferPaymentResult transferResult) {
        return PaymentService.super.toEntity(transferResult);
    }

}
