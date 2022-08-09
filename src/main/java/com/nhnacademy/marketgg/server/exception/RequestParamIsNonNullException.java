package com.nhnacademy.marketgg.server.exception;

/**
 * RequestParam 에 대해서 Null 값일 시 예외처리입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public class RequestParamIsNonNullException extends IllegalArgumentException {

    private static final String ERROR = "@RequestParam 에는 Null 값이 들어올 수 없습니다.";

    /**
     * RequestParam 이 null 일시 해당 예외 메세지를 던집니다.
     *
     * @since 1.0.0
     */
    public RequestParamIsNonNullException() {
        super(ERROR);
    }

}
