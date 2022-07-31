package com.nhnacademy.marketgg.server.exception.usedcoupon;

import com.nhnacademy.marketgg.server.exception.NotFoundException;

/**
 * 사용된 쿠폰 내역을 찾을 수 없을 때 예외처리입니다.
 *
 * @version 1.0.0
 */
public class UsedCouponNotFoundException extends NotFoundException {

    private static final String ERROR = "사용된 쿠폰내역을 찾을 수 없습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public UsedCouponNotFoundException() {
        super(ERROR);
    }

    public static class GivenCouponInMemberNotFoundException extends NotFoundException {

        private static final String MESSAGE = "해당 지급 쿠폰을 찾을 수 없습니다.";

        public GivenCouponInMemberNotFoundException() {
            super(MESSAGE);
        }
    }
}
