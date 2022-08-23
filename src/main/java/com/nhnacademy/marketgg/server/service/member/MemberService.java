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
     * 지정한 회원의 GG 패스 갱신일시를 일자로 변환하여 반환합니다..
     *
     * @param id - GG 패스 갱신일자를 확인 할 회원의 식별번호입니다.
     * @return 선택한 회원의 GG 패스 갱신일시를 일자로 반환합니다.
     * @author 박세완
     * @since 1.0.0
     */
    LocalDateTime retrievePassUpdatedAt(final Long id);

    /**
     * 지정한 회원을 GG 패스에 구독 처리합니다.
     *
     * @param id - GG 패스를 구독할 회원의 식별번호입니다.
     * @author 박세완
     * @since 1.0.0
     */
    void subscribePass(final Long id);

    /**
     * UUID 를 통해 사용자 정보를 요청합니다.
     *
     * @param uuid - 사용자를 식별할 수 있는 UUID
     * @return - 사용자 정보를 반환합니다.
     */
    MemberResponse retrieveMember(final String uuid);

    /**
     * 지정한 회원을 GG 패스에 구독해지 처리합니다.
     *
     * @param id - GG 패스를 구독해지할 회원의 식별번호입니다.
     * @author 박세완
     * @since 1.0.0
     */
    void withdrawPass(final Long id);

    /**
     * Client 에서 입력폼에 입력한 정보로 회원가입을 합니다.
     *
     * @param signupRequest - 회원가입 정보를 담은 객체입니다.
     * @since 1.0.0
     */
    void signUp(final SignupRequest signupRequest) throws JsonProcessingException;

    void withdraw(final String uuid, final MemberWithdrawRequest memberWithdrawRequest);
}
