package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.CategoryRegisterRequest;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.exception.CategorizationNotFoundException;
import com.nhnacademy.marketgg.server.repository.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.CategoryRepository;
import com.nhnacademy.marketgg.server.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategorizationRepository categorizationRepository;

    @Transactional
    @Override
    public void createCategory(CategoryRegisterRequest categoryRegisterRequest) {
        Categorization categorization =
                categorizationRepository.findById(categoryRegisterRequest.getCategorizationCode())
                                        .orElseThrow(() -> new CategorizationNotFoundException("카테고리 분류를 찾을 수 없습니다."));

        Category category = new Category(categoryRegisterRequest, categorization);

        categoryRepository.save(category);
    }

}
