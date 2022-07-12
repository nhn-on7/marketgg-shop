package com.nhnacademy.marketgg.server.exception.transaction;

public class TransactionNotFoundException extends IllegalArgumentException {

    public TransactionNotFoundException(String msg) {
        super(msg);
    }

}
