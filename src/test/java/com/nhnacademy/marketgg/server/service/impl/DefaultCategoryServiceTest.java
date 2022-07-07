package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.CategoryRequest;
import com.nhnacademy.marketgg.server.dto.CategoryResponse;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    @DisplayName("카테고리 등록 성공")
    void testCreateCategorySuccess() {
        when(categoryRepository.findById(0L)).thenReturn(Optional.of(Category.builder()
                                                                             .categoryNo(0L)
                                                                             .superCategory(null)
                                                                             .name("")
                                                                             .sequence(0)
                                                                             .code("")
                                                                             .build()));

        Category category = Category.builder()
                                    .categoryNo(1L)
                                    .superCategory(categoryRepository.findById(0L).orElseThrow())
                                    .name("채소")
                                    .sequence(1)
                                    .code("PROD")
                                    .build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.createCategory(CategoryRequest.of());

        assertThat(categoryRepository.findById(1L)).isEqualTo(Optional.of(category));
    }

    @Test
    @DisplayName("카테고리 등록 실패")
    void testCreateCategoryFail() {
        // REVIEW: assertThatThrownBy() 에 노란 라인이 뜨면서 Refactor the code of the lambda to have only one invocation possibly throwing a runtime exception.가 뜨는데 원인에 대해 알고 싶습니다.
        assertThatThrownBy(() -> categoryService.createCategory(new CategoryRequest(-10L, "", 1, "")))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 목록 조회")
    void testRetrieveCategories() {
        when(categoryRepository.findAllCategories()).thenReturn(List.of(new CategoryResponse()));

        List<CategoryResponse> responses = categoryService.retrieveCategories();

        assertThat(responses).hasSize(1);
    }

    @Test
    @DisplayName("카테고리 수정 성공")
    void testUpdateCategorySuccess() {
        when(categoryRepository.findById(0L)).thenReturn(Optional.of(Category.builder()
                                                                             .categoryNo(0L)
                                                                             .superCategory(null)
                                                                             .name("")
                                                                             .sequence(0)
                                                                             .code("")
                                                                             .build()));

        Category category = Category.builder()
                                    .categoryNo(1L)
                                    .superCategory(categoryRepository.findById(0L).orElseThrow())
                                    .name("유기농")
                                    .sequence(1)
                                    .code("PROD")
                                    .build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.updateCategory(1L, CategoryRequest.of());

        assertThat(Objects.requireNonNull(categoryRepository.findById(1L).orElse(null))
                          .getName()).isEqualTo("채소");
    }

    @Test
    @DisplayName("카테고리 수정 실패 (수정할 카테고리 존재 X)")
    void testUpdateCategoryFailWhenNotExistsCategory() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategory(-3L, new CategoryRequest(1L, "채소", 1, "PROD")))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 수정 실패 (상위 카테고리 존재 X)")
    void testUpdateCategoryFailWhenNotExistsSuperCategory() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(Category.builder()
                                                                             .categoryNo(2L)
                                                                             .superCategory(null)
                                                                             .name("채소")
                                                                             .sequence(0)
                                                                             .code("PROD")
                                                                             .build()));

        assertThatThrownBy(() -> categoryService.updateCategory(2L, new CategoryRequest(-1L, "채소", 1, "PROD")))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 삭제 성공")
    void testDeleteCategory() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(Category.builder()
                                                                                    .categoryNo(1L)
                                                                                    .superCategory(null)
                                                                                    .name("채소")
                                                                                    .sequence(0)
                                                                                    .code("PROD")
                                                                                    .build()));

        doNothing().when(categoryRepository).delete(any(Category.class));

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).delete(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 삭제 실패 (삭제할 카테고리 존재 X)")
    void testDeleteCategoryFailWhenNotExistsCategory() {
        assertThatThrownBy(() -> categoryService.deleteCategory(1L)).isInstanceOf(CategoryNotFoundException.class);
    }

}
