package com.nhnacademy.marketgg.server.exception.pointhistory;

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
