package com.nhnacademy.marketgg.server.exception.transaction;

import com.nhnacademy.marketgg.server.exception.NotFoundException;

/**
 * 트랜잭션을 찾을 수 없을 때 예외처리입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
public class TransactionNotFoundException extends NotFoundException {

    private static final String ERROR = "트랜잭션을 찾을 수 없습니다.";

    public TransactionNotFoundException() {
        super(ERROR);
    }

}
