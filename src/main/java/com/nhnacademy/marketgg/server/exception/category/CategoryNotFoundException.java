package com.nhnacademy.marketgg.server.exception.category;

import com.nhnacademy.marketgg.server.exception.NotFoundException;

/**
 * 카테고리를 찾을 수 없을 때 예외처리입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
public class CategoryNotFoundException extends NotFoundException {

    private static final String ERROR = "카테고리를 찾을 수 없습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public CategoryNotFoundException() {
        super(ERROR);
    }

}
