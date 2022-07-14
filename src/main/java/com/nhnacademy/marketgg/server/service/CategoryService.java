package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.request.CategoryUpdateRequest;

import java.util.List;

/**
 * 카테고리 서비스입니다.
 *
 * @version 1.0.0
 */
public interface CategoryService {

    /**
     * 입력받은 정보로 카테고리를 생성합니다.
     *
     * @param categoryCreateRequest 카테고리를 생성하기 위한 DTO 입니다.
     * @since 1.0.0
     */
    void createCategory(final CategoryCreateRequest categoryCreateRequest);

    /**
     * 지정한 카테고리를 반환합니다.
     *
     * @param id 반환할 카테고리의 식별번호입니다.
     * @return 선택한 카테고리를 반환합니다.
     * @since 1.0.0
     */
    CategoryRetrieveResponse retrieveCategory(final String id);

    /**
     * 전체 카테고리 목록을 반환합니다.
     *
     * @return 전체 카테고리 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<CategoryRetrieveResponse> retrieveCategories();

    /**
     * 입력받은 정보로 카테고리 정보를 수정합니다.
     *
     * @param id 수정할 카테고리의 식별번호입니다.
     * @param categoryRequest 카테고리를 수정하기위한 정보를 담은 DTO 입니다.
     * @since 1.0.0
     */
    void updateCategory(final String id, final CategoryUpdateRequest categoryRequest);

    /**
     * 지정한 카테고리를 삭제합니다.
     *
     * @param id 삭제할 카테고리의 식별번호입니다.
     * @since 1.0.0
     */
    void deleteCategory(final String id);

}
