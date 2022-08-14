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

    /**
     * 결제대행사로부터 제공받은 가상계좌 결제 정보를 바탕으로 개체를 생성합니다.
     *
     * @param request - 가상계좌 결제 생성 정보
     * @return 가상계좌 개체
     */
    default VirtualAccountPayment toEntity(VirtualAccountPaymentCreateRequest request) {
        return VirtualAccountPayment.builder()
                                    .accountType(AccountType.valueOf(request.getAccountType()))
                                    .accountNumber(request.getAccountNumber())
                                    .bank(BankCode.valueOf(request.getBank()))
                                    .customerName(request.getCustomerName())
                                    .dueDate(request.getDueDate())
                                    .refundStatus(RefundStatus.valueOf(request.getRefundStatus()))
                                    .expired(request.getExpired())
                                    .settlementStatus(SettlementStatus.valueOf(request.getSettlementStatus()))
                                    .build();
    }

}
