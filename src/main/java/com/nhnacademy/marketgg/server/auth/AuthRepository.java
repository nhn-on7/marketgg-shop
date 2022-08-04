package com.nhnacademy.marketgg.server.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.MemberNameResponse;
import java.util.List;

/**
 * auth 서버와의 전송을 위한 Repository 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public interface AuthRepository {

    List<MemberNameResponse> getNameListByUuid(final List<String> uuidList) throws JsonProcessingException;

}
