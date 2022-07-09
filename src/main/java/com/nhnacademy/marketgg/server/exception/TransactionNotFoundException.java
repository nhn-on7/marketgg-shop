package com.nhnacademy.marketgg.server.exception;

public class TransactionNotFoundException extends IllegalArgumentException {

    public TransactionNotFoundException(String ex) {
        super(ex);
    }

}
