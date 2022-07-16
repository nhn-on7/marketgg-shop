package com.nhnacademy.marketgg.server.repository.category;

import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CategoryRepositoryCustom {

    /**
     * 지정한 카테고리를 반환하는 메소드입니다.
     *
     * @param id - 반환할 카테고리의 식별번호입니다.
     * @return - 지정한 카테고리의 정보를 담은 객체를 반환합니다.
     * @since 1.0.0
     */
    CategoryRetrieveResponse findByCode(final String id);

    /**
     * 전체 카테고리 목록을 반환하는 메소드입니다.
     *
     * @return 카테고리 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<CategoryRetrieveResponse> findAllCategories();
    
}
