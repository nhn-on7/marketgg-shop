package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.response.category.CategorizationRetrieveResponse;
import java.util.List;

/**
 * 카테고리 분류표 서비스입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public interface CategorizationService {

    /**
     * 전체 카테고리 분류표 목록을 반환합니다.
     *
     * @return 전체 카테고리 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<CategorizationRetrieveResponse> retrieveCategorizations();

}
