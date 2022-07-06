package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.CategoryRequest;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.exception.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.repository.CategoryRepository;
import com.nhnacademy.marketgg.server.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        Category superCategory = categoryRepository.findById(categoryRequest.getSuperCategoryNo())
                                                   .orElseThrow(() -> new CategoryNotFoundException(
                                                           "카테고리를 찾을 수 없습니다."));

        categoryRepository.save(new Category(superCategory, categoryRequest));
    }

    @Transactional
    @Override
    public void updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                                              .orElseThrow(
                                                      () -> new CategoryNotFoundException(
                                                              "카테고리를 찾을 수 없습니다."));

        category.setSuperCategory(categoryRepository.findById(categoryRequest.getSuperCategoryNo())
                                                    .orElseThrow(
                                                            () -> new CategoryNotFoundException(
                                                                    "카테고리를 찾을 수 없습니다.")));
        category.setName(categoryRequest.getName());
        category.setSequence(category.getSequence());
        category.setCode(category.getCode());

        categoryRepository.save(category);
    }

}
