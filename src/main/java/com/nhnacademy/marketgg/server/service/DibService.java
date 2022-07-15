package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.DibCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.DibDeleteRequest;
import com.nhnacademy.marketgg.server.dto.response.DibRetrieveResponse;

import java.util.List;

/**
 * 찜 서비스입니다.
 *
 * @version 1.0.0
 */
public interface DibService {

    /**
     * 찜을 등록합니다.
     *
     * @param dibCreateRequest - 찜을 등록하기 위한 DTO 입니다.
     * @since 1.0.0
     */
    void createDib(DibCreateRequest dibCreateRequest);

    /**
     * 회원의 찜 목록을 반환합니다.
     *
     * @param memberNo - 찜 목록을 조회할 회원의 고유 번호입니다.
     * @return 회원의 찜 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<DibRetrieveResponse> retrieveDibs(Long memberNo);

    /**
     * 회원의 찜 하나를 삭제합니다.
     *
     * @param dibDeleteRequest - 삭제할 찜의 회원번호와 상품번호가 있는 DTO 입니다.
     * @since 1.0.0
     */
    void deleteDib(DibDeleteRequest dibDeleteRequest);

}
