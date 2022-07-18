package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.response.DibRetrieveResponse;

import java.util.List;

/**
 * 찜 서비스입니다.
 *
 * @version 1.0.0
 */
public interface DibService {

//here
    void createDib(final Long memberId, final Long productId);

    /**
     * 회원의 찜 목록을 반환합니다.
     *
     * @param memberId - 찜 목록을 조회할 회원의 고유 번호입니다.
     * @return 회원의 찜 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<DibRetrieveResponse> retrieveDibs(final Long memberId);

    /**
     * 회원의 찜 하나를 삭제합니다.
     *
     * @since 1.0.0
     */
    void deleteDib(Long memberId, Long productId);

}
