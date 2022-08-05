package com.nhnacademy.marketgg.server.exception.image;

import com.nhnacademy.marketgg.server.exception.NotFoundException;

/**
 * 아미지를 찾을 수 없을 때 예외처리입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
public class ImageNotFoundException extends NotFoundException {

    private static final String ERROR = "이미지를 찾을 수 없습니다";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public ImageNotFoundException() {
        super(ERROR);
    }

}
