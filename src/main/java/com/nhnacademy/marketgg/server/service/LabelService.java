package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.LabelCreateRequest;

import com.nhnacademy.marketgg.server.dto.response.LabelRetrieveResponse;
import java.util.List;

/**
 * 라벨 서비스입니다.
 *
 * @version 1.0.0
 */
public interface LabelService {

    /**
     * 입력받은 정보로 라벨을 생성합니다.
     *
     * @param labelCreateRequest 라벨을 생성하기위한 정보를 담은 DTO 입니다.
     * @since 1.0.0
     */
    void createLabel(final LabelCreateRequest labelCreateRequest);

    /**
     * 전체 라벨 목록을 반환합니다.
     *
     * @return 전체 라벨 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<LabelRetrieveResponse> retrieveLabels();

    /**
     * 지정한 라벨을 삭제합니다.
     *
     * @param id 삭제할 라벨의 식별번호입니다.
     * @since 1.0.0
     */
    void deleteLabel(final Long id);

}
