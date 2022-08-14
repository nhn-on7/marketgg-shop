package com.nhnacademy.marketgg.server.dto.request.payment;

import com.nhnacademy.marketgg.server.constant.payment.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PaymentRequest {

    private PaymentMethod paymentType;

    private Long amount;

    private String orderName;

    private String buyerName;

    private String buyerEmail;

    // public Payment toEntity() {
    //     return Payment.builder()
    //                   .orderId(UUID.randomUUID().toString())
    //                   .paymentType(paymentType)
    //                   .amount(amount)
    //                   .orderName(orderName)
    //                   .buyerEmail(buyerEmail)
    //                   .build();
    // }

}
