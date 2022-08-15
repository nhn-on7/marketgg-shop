package com.nhnacademy.marketgg.server.exception.coupon;

public class CouponNotOverMinimumMoneyException extends RuntimeException {

    private static final String ERROR = "최소주문금액을 확인해주세요.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public CouponNotOverMinimumMoneyException() {
        super(ERROR);
    }
}
