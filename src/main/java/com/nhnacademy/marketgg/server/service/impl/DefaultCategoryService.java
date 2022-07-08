package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.CategoryUpdateRequest;
import com.nhnacademy.marketgg.server.exception.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.dto.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.exception.CategorizationNotFoundException;
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
    public void createCategory(final CategoryCreateRequest categoryCreateRequest) {
        Categorization categorization =
                categorizationRepository.findById(categoryCreateRequest.getCategorizationCode())
                                        .orElseThrow(() -> new CategorizationNotFoundException("카테고리 분류를 찾을 수 없습니다."));

        Category category = new Category(categoryCreateRequest, categorization);

        categoryRepository.save(category);
    }
        
    @Override
    public List<CategoryRetrieveResponse> retrieveCategories() {
        return categoryRepository.findAllCategories();
    }
    
    @Transactional
    @Override
    public void updateCategory(final Long categoryId, final CategoryUpdateRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(() -> new CategoryNotFoundException("카테고리를 찾을 수 없습니다."));
        Categorization categorization =
                categorizationRepository.findById(categoryRequest.getCategorizationCode())
                                        .orElseThrow(() -> new CategorizationNotFoundException("카테고리 분류를 찾을 수 없습니다."));

        category.updateCategory(categoryRequest, categorization);

        categoryRepository.save(category);
    }
    
    @Transactional
    @Override
    public void deleteCategory(final Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(() -> new CategoryNotFoundException("카테고리를 찾을 수 없습니다."));

        categoryRepository.delete(category);
    }

}
