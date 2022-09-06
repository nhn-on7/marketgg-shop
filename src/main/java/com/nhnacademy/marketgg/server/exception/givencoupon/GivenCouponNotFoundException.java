package com.nhnacademy.marketgg.server.exception.givencoupon;

import com.nhnacademy.marketgg.server.exception.NotFoundException;

/**
 * 지급된 쿠폰내역을 찾을 수 없을 때 예외처리입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
public class GivenCouponNotFoundException extends NotFoundException {

    private static final String ERROR = "지급된 쿠폰내역을 찾을 수 없습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public GivenCouponNotFoundException() {
        super(ERROR);
    }

    /**
     * 쿠폰 지급할 회원의 정보를 찾을 수 없을 때 예외처리입니다.
     *
     * @version 1.0.0
     */
    public static class MemberInfoNotFoundException extends NotFoundException {

        private static final String ERROR = "쿠폰 지급할 회원 정보를 찾을 수 없습니다.";

        public MemberInfoNotFoundException() {
            super(ERROR);
        }
    }

    /**
     * 지급할 쿠폰의 정보를 찾을 수 없을 때 예외처리입니다.
     *
     * @version 1.0.0
     */
    public static class CouponInfoNotFoundException extends NotFoundException {

        private static final String ERROR = "지급할 쿠폰의 정보를 찾을 수 없습니다.";

        public CouponInfoNotFoundException() {
            super(ERROR);
        }
    }

    /**
     * 이미 회원에게 지급된 쿠폰일 때 예외처리입니다.
     *
     * @version 1.0.0
     */
    public static class CouponAlreadyExistException extends NotFoundException {

        private static final String ERROR = "이미 지급된 쿠폰입니다.";

        public CouponAlreadyExistException() {
            super(ERROR);
        }
    }

}
