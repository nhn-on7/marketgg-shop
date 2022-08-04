package com.nhnacademy.marketgg.server.repository.category;

import com.nhnacademy.marketgg.server.dto.response.category.CategoryRetrieveResponse;
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
     * 카테고리 이름으로 카테고리 식별번호를 조회하는 메소드입니다.
     *
     * @param name - 조회할 카테고리 이름입니다.
     * @return - 카테고리 식별번호를 반환합니다.
     * @since 1.0.0
     */
    String retrieveCategoryIdByName(final String name);

    /**
     * 전체 카테고리 목록을 반환하는 메소드입니다.
     *
     * @return 카테고리 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<CategoryRetrieveResponse> findAllCategories();

    /**
     * 카테고리 분류표에 따른 카테고리 목록을 반환하는 메소드입니다.
     *
     * @param categorizationId - 카테고리 분류표 식별번호입니다.
     * @return 조회한 카테고리 목록을 List 로 반환합니다.
     */
    List<CategoryRetrieveResponse> findByCategorizationCode(final String categorizationId);

}
