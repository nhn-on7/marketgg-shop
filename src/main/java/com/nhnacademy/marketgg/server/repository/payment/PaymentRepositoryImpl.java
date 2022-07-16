package com.nhnacademy.marketgg.server.repository.payment;

import com.nhnacademy.marketgg.server.entity.Payment;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class PaymentRepositoryImpl extends QuerydslRepositorySupport implements PaymentRepositoryCustom {

    public PaymentRepositoryImpl() {
        super(Payment.class);
    }

}
