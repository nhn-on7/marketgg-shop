package com.nhnacademy.marketgg.server.service.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.request.member.MemberWithdrawRequest;
import com.nhnacademy.marketgg.server.dto.request.member.SignupRequest;
import com.nhnacademy.marketgg.server.dto.response.member.MemberResponse;
import java.time.LocalDateTime;

/**
 * 회원 서비스입니다.
 *
 * @version 1.0.0
 */
public interface MemberService {

    /**
     * UUID 를 통해 사용자 정보를 요청합니다.
     *
     * @param uuid - 사용자를 식별할 수 있는 UUID
     * @return - 사용자 정보를 반환합니다.
     */
    MemberResponse retrieveMember(final String uuid);

    /**
     * Client 에서 입력폼에 입력한 정보로 회원가입을 합니다.
     *
     * @param signupRequest - 회원가입 정보를 담은 객체입니다.
     * @since 1.0.0
     */
    void signUp(final SignupRequest signupRequest) throws JsonProcessingException;

    void withdraw(final String uuid, final MemberWithdrawRequest memberWithdrawRequest);
}
