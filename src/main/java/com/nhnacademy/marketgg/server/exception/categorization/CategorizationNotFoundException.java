package com.nhnacademy.marketgg.server.exception.categorization;

/**
 * 카테고리 분류를 찾을 수 없을 때 예외처리입니다.
 *
 * @version 1.0.0
 */
public class CategorizationNotFoundException extends IllegalArgumentException {

    /**
     * 에러 메세지를 지정합니다.
     *
     * @since 1.0.0
     */
    private static final String ERROR = "카테고리 분류를 찾을 수 없습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public CategorizationNotFoundException() {
        super(ERROR);
    }

}
