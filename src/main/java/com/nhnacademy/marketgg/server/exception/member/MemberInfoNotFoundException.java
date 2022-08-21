package com.nhnacademy.marketgg.server.exception.member;

import com.nhnacademy.marketgg.server.exception.NotFoundException;

/**
 * 회원 정보를 Auth DB 에서 찾을 수 없을 때 예외처리입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
public class MemberInfoNotFoundException extends NotFoundException {

    private final String error = "회원 정보를 찾을 수 없습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public MemberInfoNotFoundException(final String error) {
        super(error);
    }

}
