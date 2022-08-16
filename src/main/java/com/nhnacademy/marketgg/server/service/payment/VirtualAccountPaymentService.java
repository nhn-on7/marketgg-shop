package com.nhnacademy.marketgg.server.service.payment;

import com.nhnacademy.marketgg.server.constant.payment.AccountType;
import com.nhnacademy.marketgg.server.constant.payment.BankCode;
import com.nhnacademy.marketgg.server.constant.payment.RefundStatus;
import com.nhnacademy.marketgg.server.constant.payment.SettlementStatus;
import com.nhnacademy.marketgg.server.dto.payment.request.VirtualAccountPaymentCreateRequest;
import com.nhnacademy.marketgg.server.entity.payment.VirtualAccountPayment;

/**
 * 가상계좌 결제에 대한 비즈니스 처리를 담당합니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
public interface VirtualAccountPaymentService extends PaymentService {
}
