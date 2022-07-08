package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.marketgg.server.dto.CategoryUpdateRequest;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.exception.CategorizationNotFoundException;
import com.nhnacademy.marketgg.server.exception.CategoryNotFoundException;
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
    @DisplayName("카테고리 수정 성공")
    void testUpdateCategorySuccess() {
        CategoryUpdateRequest categoryRequest = new CategoryUpdateRequest();
        ReflectionTestUtils.setField(categoryRequest, "categorizationCode", "001");
        ReflectionTestUtils.setField(categoryRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryRequest, "sequence", 1);
        when(categoryRepository.findById(anyLong())).thenReturn(
                Optional.of(new Category("001", null, "과일", 2)));
        when(categorizationRepository.findById(anyString())).thenReturn(
                Optional.of(new Categorization("001", "상품", "product")));

        categoryService.updateCategory(1L, categoryRequest);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 수정 실패(카테고리 존재 X)")
    void testUpdateCategoryFailWhenNoCategory() {
        CategoryUpdateRequest categoryRequest = new CategoryUpdateRequest();
        ReflectionTestUtils.setField(categoryRequest, "categorizationCode", "001");
        ReflectionTestUtils.setField(categoryRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryRequest, "sequence", 1);
        when(categoryRepository.findById(anyLong())).thenReturn(
                Optional.empty());
        when(categorizationRepository.findById(anyString())).thenReturn(
                Optional.of(new Categorization("001", "상품", "product")));

        assertThatThrownBy(() -> categoryService.updateCategory(1L, categoryRequest))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 수정 실패(카테고리 분류 존재 X)")
    void testUpdateCategoryFailWhenNoCategorization() {
        CategoryUpdateRequest categoryRequest = new CategoryUpdateRequest();
        ReflectionTestUtils.setField(categoryRequest, "categorizationCode", "001");
        ReflectionTestUtils.setField(categoryRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryRequest, "sequence", 1);
        when(categoryRepository.findById(anyLong())).thenReturn(
                Optional.of(new Category("001", null, "과일", 2)));
        when(categorizationRepository.findById(anyString())).thenReturn(
                Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategory(1L, categoryRequest))
                .isInstanceOf(CategorizationNotFoundException.class);
    }

}
