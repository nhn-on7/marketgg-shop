package com.nhnacademy.marketgg.server.exception.order;

/**
 * 주문하는 회원정보가 로그인한 회원 정보와 다를 때 예외처리입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
public class OrderMemberNotMatchedException extends RuntimeException {

    private static final String ERROR = "주문자와 로그인 회원한 정보가 다릅니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public OrderMemberNotMatchedException() {
        super(ERROR);
    }

}
