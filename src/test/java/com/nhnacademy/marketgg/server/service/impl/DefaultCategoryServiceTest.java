package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.CategoryRegisterRequest;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.exception.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.repository.CategoryRepository;
import com.nhnacademy.marketgg.server.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class DefaultCategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @DisplayName("카테고리 등록 성공")
    @Test
    void testCreateCategorySuccess() {

        when(categoryRepository.findById(0L)).thenReturn(Optional.of(Category.builder()
                .categoryNo(0L).superCategory(null).name("").sequence(0).code("")
                .build()));

        Category category = Category.builder()
                                    .categoryNo(1L)
                                    .superCategory(categoryRepository.findById(0L).orElseThrow())
                                    .name("채소")
                                    .sequence(1)
                                    .code("PROD")
                                    .build();

        categoryService.createCategory(new CategoryRegisterRequest(
                category.getSuperCategory().getCategoryNo(),
                category.getName(),
                category.getSequence(),
                category.getCode()));

        assertThat(categoryRepository.findById(1L)).isEqualTo(Optional.of(category));
    }

    @DisplayName("카테고리 등록 실패")
    @Test
    void testCreateCategoryFail() {
        assertThatThrownBy(()-> categoryService.createCategory(
                new CategoryRegisterRequest(-10L, "", 1, "")))
                .isInstanceOf(CategoryNotFoundException.class);
    }

}
