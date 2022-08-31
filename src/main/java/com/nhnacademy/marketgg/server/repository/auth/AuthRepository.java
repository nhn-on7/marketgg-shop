package com.nhnacademy.marketgg.server.repository.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoRequest;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoResponse;
import com.nhnacademy.marketgg.server.dto.info.MemberNameResponse;
import com.nhnacademy.marketgg.server.dto.request.member.MemberUpdateRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberWithdrawRequest;
import com.nhnacademy.marketgg.server.dto.request.member.SignupRequest;
import com.nhnacademy.marketgg.server.dto.response.auth.UuidTokenResponse;
import com.nhnacademy.marketgg.server.dto.response.member.SignupResponse;

import java.util.List;

/**
 * auth 서버와의 전송을 위한 Repository 입니다.
 *
 * @author 박세완
 * @author 민아영
 * @author 김정민
 * @version 1.0.0
 */
public interface AuthRepository {

    /**
     * 회원의 uuid 목록을 전송하여 이름 목록을 조회하기 위한 메소드입니다.
     *
     * @param uuidList - 회원의 uuid 목록입니다.
     * @return uuid 목록에 해당하는 회원의 이름 목록을 반환합니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    List<MemberNameResponse> getNameListByUuid(final List<String> uuidList) throws JsonProcessingException;

    /**
     * 회원의 uuid 를 통해 회원의 정보를 얻기 위한 메소드입니다.
     *
     * @param memberInfoRequest - 회원의 정보를 요청하기 위한 DTO 객체입니다.
     * @return 회원의 정보를 반환합니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    ShopResult<MemberInfoResponse> getMemberInfo(final MemberInfoRequest memberInfoRequest)
            throws JsonProcessingException;

    ShopResult<SignupResponse> signup(final SignupRequest signUpRequest) throws JsonProcessingException;

    void withdraw(final MemberWithdrawRequest memberWithdrawRequest, final String token) throws JsonProcessingException;

    ShopResult<UuidTokenResponse> update(final MemberUpdateRequest memberUpdateRequest, final String token);
}
