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

@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategorizationRepository categorizationRepository;

    @Transactional
    @Override
    public void createCategory(final CategoryCreateRequest createRequest) {
        Categorization categorization =
                categorizationRepository.findById(createRequest.getCategorizationCode())
                                        .orElseThrow(CategorizationNotFoundException::new);

        Category category = new Category(createRequest, categorization);

        categoryRepository.save(category);
    }

    @Override
    public CategoryRetrieveResponse retrieveCategory(final String id) {
        return categoryRepository.findByCode(id);
    }

    @Override
    public List<CategoryRetrieveResponse> retrieveCategories() {
        return categoryRepository.findAllCategories();
    }

    @Transactional
    @Override
    public void updateCategory(final String id, final CategoryUpdateRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                                              .orElseThrow(CategoryNotFoundException::new);

        category.updateCategory(categoryRequest);

        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void deleteCategory(final String id) {
        Category category = categoryRepository.findById(id)
                                              .orElseThrow(CategoryNotFoundException::new);

        categoryRepository.delete(category);
    }

}
