package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.CategoryRegisterRequest;
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
    public void createCategory(CategoryRegisterRequest categoryRequest) {
        Category superCategory = categoryRepository.findById(categoryRequest.getSuperCategoryNo())
                                                   .orElseThrow(()->new CategoryNotFoundException("카테고리를 찾을 수 없습니다."));

        categoryRepository.save(new Category(superCategory, categoryRequest));
    }

}
