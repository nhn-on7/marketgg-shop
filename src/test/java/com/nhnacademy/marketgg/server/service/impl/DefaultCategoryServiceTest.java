package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.marketgg.server.dto.CategoryRegisterRequest;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.exception.CategorizationNotFoundException;
import com.nhnacademy.marketgg.server.repository.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.CategoryRepository;
import com.nhnacademy.marketgg.server.service.CategoryService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DefaultCategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    CategorizationRepository categorizationRepository;

    @Test
    @DisplayName("카테고리 생성 성공")
    void testCreateCategorySuccess() {
        CategoryRegisterRequest categoryRegisterRequest = new CategoryRegisterRequest();
        ReflectionTestUtils.setField(categoryRegisterRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(categoryRegisterRequest, "categorizationCode", "001");
        ReflectionTestUtils.setField(categoryRegisterRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryRegisterRequest, "sequence", 1);
        when(categorizationRepository.findById(anyString())).thenReturn(
                Optional.of(new Categorization("111", "상품", "product")));

        categoryService.createCategory(categoryRegisterRequest);

        verify(categorizationRepository, times(1)).findById(anyString());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 생성 실패")
    void testCreateCategoryFail() {
        CategoryRegisterRequest categoryRegisterRequest = new CategoryRegisterRequest();
        ReflectionTestUtils.setField(categoryRegisterRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(categoryRegisterRequest, "categorizationCode", "001");
        ReflectionTestUtils.setField(categoryRegisterRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryRegisterRequest, "sequence", 1);
        when(categorizationRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.createCategory(categoryRegisterRequest))
                .isInstanceOf(CategorizationNotFoundException.class);
    }

}