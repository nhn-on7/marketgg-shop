package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.exception.categorization.CategorizationNotFoundException;
import com.nhnacademy.marketgg.server.exception.category.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.repository.categorization.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultCategoryServiceTest {

    @InjectMocks
    DefaultCategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategorizationRepository categorizationRepository;

    private CategoryCreateRequest categoryCreateRequest;
    private CategoryUpdateRequest categoryUpdateRequest;
    private CategorizationCreateRequest categorizationCreateRequest;

    @BeforeEach
    void setUp() {
        categoryCreateRequest = new CategoryCreateRequest();
        categoryUpdateRequest = new CategoryUpdateRequest();
        categorizationCreateRequest = new CategorizationCreateRequest();

        ReflectionTestUtils.setField(categoryCreateRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(categoryCreateRequest, "categorizationCode", "001");
        ReflectionTestUtils.setField(categoryCreateRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryCreateRequest, "sequence", 1);

        ReflectionTestUtils.setField(categoryUpdateRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryUpdateRequest, "sequence", 1);

        ReflectionTestUtils.setField(categorizationCreateRequest, "categorizationCode", "111");
        ReflectionTestUtils.setField(categorizationCreateRequest, "name", "상품");
        ReflectionTestUtils.setField(categorizationCreateRequest, "alias", "PRODUCT");
    }

    @Test
    @DisplayName("카테고리 생성 성공")
    void testCreateCategorySuccess() {
        when(categorizationRepository.findById(anyString()))
                .thenReturn(Optional.of(new Categorization(categorizationCreateRequest)));

        categoryService.createCategory(categoryCreateRequest);

        verify(categorizationRepository, times(1)).findById(anyString());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 생성 실패")
    void testCreateCategoryFail() {
        when(categorizationRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.createCategory(categoryCreateRequest))
                .isInstanceOf(CategorizationNotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 단건 조회")
    void testRetrieveCategory() {
        when(categoryRepository.findByCode(anyString())).thenReturn(null);

        categoryService.retrieveCategory("001");

        verify(categoryRepository, times(1)).findByCode(anyString());
    }

    @Test
    @DisplayName("카테고리 목록 조회")
    void testRetrieveCategories() {
        when(categoryRepository.findAllCategories())
                .thenReturn(List.of(new CategoryRetrieveResponse("001", "categorization", "categoryName", 1)));

        List<CategoryRetrieveResponse> categoryResponses = categoryService.retrieveCategories();

        assertThat(categoryResponses).hasSize(1);
    }

    @Test
    @DisplayName("카테고리 수정 성공")
    void testUpdateCategorySuccess() {
        when(categoryRepository.findById(anyString()))
                .thenReturn(Optional.of(new Category(categoryCreateRequest, new Categorization(
                        categorizationCreateRequest))));

        categoryService.updateCategory("001", categoryUpdateRequest);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 수정 실패(카테고리 존재 X)")
    void testUpdateCategoryFailWhenNoCategory() {
        when(categoryRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategory("001", categoryUpdateRequest))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 삭제 성공")
    void testDeleteCategory() {
        when(categoryRepository.findById(anyString()))
                .thenReturn(Optional.of(new Category(categoryCreateRequest, new Categorization(
                        categorizationCreateRequest))));
        doNothing().when(categoryRepository).delete(any(Category.class));

        categoryService.deleteCategory("001001");

        verify(categoryRepository, times(1)).delete(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 삭제 실패 (삭제할 카테고리 존재 X)")
    void testDeleteCategoryFailWhenNotExistsCategory() {
        assertThatThrownBy(() -> categoryService.deleteCategory("99999"))
                .isInstanceOf(CategoryNotFoundException.class);
    }

}
