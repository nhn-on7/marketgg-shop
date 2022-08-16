package com.nhnacademy.marketgg.server.service.payment;

import com.nhnacademy.marketgg.server.dto.payment.result.MobilePhonePaymentResult;
import com.nhnacademy.marketgg.server.entity.payment.MobilePhonePayment;

/**
 * 휴대폰 결제에 대한 비즈니스 처리를 담당합니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
public interface MobilePhonePaymentService extends PaymentService {

    @Override
    default MobilePhonePayment toEntity(MobilePhonePaymentResult mobilePhoneResult) {
        return PaymentService.super.toEntity(mobilePhoneResult);
    }

}
