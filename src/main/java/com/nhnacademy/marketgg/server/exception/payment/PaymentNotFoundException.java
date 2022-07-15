package com.nhnacademy.marketgg.server.exception.payment;

/**
 * 결제내역을 찾을 수 없을 때 예외처리입니다.
 *
 * @version 1.0.0
 */
public class PaymentNotFoundException extends IllegalArgumentException {

    private static final String ERROR = "결제내역을 찾을 수 없습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public PaymentNotFoundException() {
        super(ERROR);
    }

}
