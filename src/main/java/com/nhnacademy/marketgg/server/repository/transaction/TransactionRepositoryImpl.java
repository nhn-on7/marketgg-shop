package com.nhnacademy.marketgg.server.repository.transaction;

import com.nhnacademy.marketgg.server.entity.Transaction;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class TransactionRepositoryImpl extends QuerydslRepositorySupport implements TransactionRepositoryCustom {

    public TransactionRepositoryImpl() {
        super(Transaction.class);
    }

}
