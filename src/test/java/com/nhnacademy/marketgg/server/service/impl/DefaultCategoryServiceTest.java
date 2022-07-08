package com.nhnacademy.marketgg.server.service.impl;

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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class DefaultCategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 삭제 성공")
    void testDeleteCategory() {
        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Category("001001", null, "친환경", 1)));
        doNothing().when(categoryRepository).delete(any(Category.class));

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).delete(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 삭제 실패 (삭제할 카테고리 존재 X)")
    void testDeleteCategoryFailWhenNotExistsCategory() {
        assertThatThrownBy(() -> categoryService.deleteCategory(1L))
                .isInstanceOf(CategoryNotFoundException.class);
    }

}
