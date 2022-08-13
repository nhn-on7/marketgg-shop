package com.nhnacademy.marketgg.server.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoRequest;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoResponse;
import com.nhnacademy.marketgg.server.dto.info.MemberNameResponse;
import java.util.List;

/**
 * auth 서버와의 전송을 위한 Repository 입니다.
 *
 * @author 박세완
 * @author 민아영
 * @version 1.0.0
 */
public interface AuthRepository {

    /**
     * auth 서버에서 uuid 목록을 전송해 이름 목록을 가져옵니다.
     *
     * @param uuidList
     * @return
     * @throws JsonProcessingException
     */
    List<MemberNameResponse> getNameListByUuid(final List<String> uuidList) throws JsonProcessingException;

    /**
     * auth 서버에서 uuid 을 전송해 사용자 정보를 가져옵니다.
     *
     * @param uuid
     * @return
     * @throws JsonProcessingException
     */
    MemberInfoResponse getNameByUuid(final MemberInfoRequest uuid) throws JsonProcessingException;

}
