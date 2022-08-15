package com.nhnacademy.marketgg.server.exception.coupon;

public class CouponNotValidException extends RuntimeException {

    private static final String ERROR = "쿠폰이 이미 사용되었습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public CouponNotValidException() {
        super(ERROR);
    }
}
