package com.nhnacademy.marketgg.server.exception.pointhistory;

/**
 * 보유포인트가 충분하지 않을 때 예외처리입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
public class PointNotEnoughException extends RuntimeException {

    private static final String ERROR = "포인트가 충분하지 않습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public PointNotEnoughException() {
        super(ERROR);
    }
}
