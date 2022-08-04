package com.nhnacademy.marketgg.server.exception.customerservicepost;

import com.nhnacademy.marketgg.server.exception.NotFoundException;

/**
 * 고객센터 게시판을 찾을 수 없을 때 예외처리입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
public class CustomerServicePostNotFoundException extends NotFoundException {

    private static final String ERROR = "고객센터 게시판을 찾을 수 없습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public CustomerServicePostNotFoundException() {
        super(ERROR);
    }

}
