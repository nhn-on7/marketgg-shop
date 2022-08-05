package com.nhnacademy.marketgg.server.repository.categorization;

import com.nhnacademy.marketgg.server.dto.response.category.CategorizationRetrieveResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CategorizationRepositoryCustom {

    /**
     * 전체 카테고리 분류 목록을 반환하는 메소드입니다.
     *
     * @return 카테고리 분류 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<CategorizationRetrieveResponse> findAllCategorization();

}
