package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.exception.categorization.CategorizationNotFoundException;
import com.nhnacademy.marketgg.server.exception.category.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.repository.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.CategoryRepository;
import com.nhnacademy.marketgg.server.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 카테고리 서비스를 구현한 구현체입니다.
 *
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

    /**
     * 카테고리 Repository 입니다.
     *
     * @since 1.0.0
     */
    private final CategoryRepository categoryRepository;
    /**
     * 카테고리 분류표 Repository 입니다.
     *
     * @since 1.0.0
     */
    private final CategorizationRepository categorizationRepository;

    /**
     * 입력받은 정보로 카테고리를 생성하기위한 메소드입니다.
     *
     * @param createRequest - 카테고리를 생성하기위한 정보를 담은 DTO 입니다.
     * @since 1.0.0
     */
    @Transactional
    @Override
    public void createCategory(final CategoryCreateRequest createRequest) {
        Categorization categorization =
                categorizationRepository.findById(createRequest.getCategorizationCode())
                                        .orElseThrow(CategorizationNotFoundException::new);

        Category category = new Category(createRequest, categorization);

        categoryRepository.save(category);
    }

    /**
     * 지정한 카테고리를 반환하기위한 메소드입니다.
     *
     * @param id - 반환할 카테고리의 식별번호입니다.
     * @return 선택한 카테고리를 반환합니다.
     * @since 1.0.0
     */
    @Override
    public CategoryRetrieveResponse retrieveCategory(final String id) {
        return categoryRepository.findByCode(id);
    }

    /**
     * 전체 카테고리 목록을 반환하기위한 메소드입니다.
     *
     * @return 전체 카테고리 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @Override
    public List<CategoryRetrieveResponse> retrieveCategories() {
        return categoryRepository.findAllCategories();
    }

    /**
     * 입력받은 정보로 카테고리 정보를 수정하기위한 메소드입니다.
     *
     * @param id - 수정할 카테고리의 식별번호입니다.
     * @param categoryRequest 카테고리를 수정하기위한 정보를 담은 DTO 입니다.
     * @since 1.0.0
     */
    @Transactional
    @Override
    public void updateCategory(final String id, final CategoryUpdateRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                                              .orElseThrow(CategoryNotFoundException::new);

        category.updateCategory(categoryRequest);

        categoryRepository.save(category);
    }

    /**
     * 지정한 카테고리를 삭제하기위한 메소드입니다.
     *
     * @param id - 삭제할 카테고리의 식별번호입니다.
     * @since 1.0.0
     */
    @Transactional
    @Override
    public void deleteCategory(final String id) {
        Category category = categoryRepository.findById(id)
                                              .orElseThrow(CategoryNotFoundException::new);

        categoryRepository.delete(category);
    }

}
