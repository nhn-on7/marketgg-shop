package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticBoardRepository;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticProductRepository;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.exception.categorization.CategorizationNotFoundException;
import com.nhnacademy.marketgg.server.exception.category.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.repository.categorization.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
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
    @Mock
    ElasticProductRepository elasticProductRepository;
    @Mock
    ElasticBoardRepository elasticBoardRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    CustomerServicePostRepository customerServicePostRepository;

    private static CategoryCreateRequest categoryCreateRequest;
    private static CategoryUpdateRequest categoryUpdateRequest;
    private static CategorizationCreateRequest categorizationCreateRequest;

    @BeforeAll
    static void beforeAll() {
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
        given(categorizationRepository.findById(anyString()))
                .willReturn(Optional.of(new Categorization(categorizationCreateRequest)));

        categoryService.createCategory(categoryCreateRequest);

        then(categorizationRepository).should().findById(anyString());
        then(categoryRepository).should().save(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 생성 실패")
    void testCreateCategoryFail() {
        given(categorizationRepository.findById(anyString())).willReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.createCategory(categoryCreateRequest))
                .isInstanceOf(CategorizationNotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 단건 조회")
    void testRetrieveCategory() {
        given(categoryRepository.findByCode(anyString())).willReturn(null);

        categoryService.retrieveCategory("001");

        then(categoryRepository).should().findByCode(anyString());
    }

    @Test
    @DisplayName("카테고리 분류표별 카테고리 목록 조회")
    void testRetrieveCategoriesByCategorization() {
        given(categoryService.retrieveCategoriesByCategorization(anyString()))
                .willReturn(List.of(new CategoryRetrieveResponse("101", "categorizationName",
                                                                 "categoryName", 1)));

        List<CategoryRetrieveResponse> categoryResponses =
                categoryService.retrieveCategoriesByCategorization("100");

        assertThat(categoryResponses).hasSize(1);
    }

    @Test
    @DisplayName("카테고리 목록 조회")
    void testRetrieveCategories() {
        given(categoryRepository.findAllCategories())
                .willReturn(List.of(new CategoryRetrieveResponse("001", "categorization",
                                                                 "categoryName", 1)));

        List<CategoryRetrieveResponse> categoryResponses = categoryService.retrieveCategories();

        assertThat(categoryResponses).hasSize(1);
    }

    @Test
    @DisplayName("카테고리 수정 성공")
    void testUpdateCategorySuccess() {
        given(categoryRepository.findById(anyString()))
                .willReturn(Optional.of(new Category(categoryCreateRequest, new Categorization(
                        categorizationCreateRequest))));

        categoryService.updateCategory("001", categoryUpdateRequest);

        then(categoryRepository).should().save(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 수정 실패(카테고리 존재 X)")
    void testUpdateCategoryFailWhenNoCategory() {
        given(categoryRepository.findById(anyString())).willReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategory("001", categoryUpdateRequest))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 삭제 성공")
    void testDeleteCategory() {
        given(categoryRepository.findById(anyString()))
                .willReturn(Optional.of(new Category(categoryCreateRequest, new Categorization(
                        categorizationCreateRequest))));
        doNothing().when(categoryRepository).delete(any(Category.class));
        doNothing().when(elasticBoardRepository).deleteAllByCategoryCode(anyString());
        doNothing().when(elasticProductRepository).deleteAllByCategoryCode(anyString());
        given(productRepository.existsByCategory(any(Category.class))).willReturn(Boolean.FALSE);
        given(customerServicePostRepository.existsByCategory(any(Category.class))).willReturn(Boolean.FALSE);

        categoryService.deleteCategory("001001");

        then(categoryRepository).should().delete(any(Category.class));
        then(elasticBoardRepository).should().deleteAllByCategoryCode(anyString());
        then(elasticProductRepository).should().deleteAllByCategoryCode(anyString());
    }

    @Test
    @DisplayName("카테고리 연관 존재 시 삭제 실패")
    void testDeleteCategoryFailByExist() {
        given(categoryRepository.findById(anyString()))
                .willReturn(Optional.of(new Category(categoryCreateRequest, new Categorization(
                        categorizationCreateRequest))));
        given(productRepository.existsByCategory(any(Category.class))).willReturn(Boolean.TRUE);

        categoryService.deleteCategory("001001");

        then(categoryRepository).should().findById(anyString());
        then(productRepository).should().existsByCategory(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 삭제 실패 (삭제할 카테고리 존재 X)")
    void testDeleteCategoryFailWhenNotExistsCategory() {
        assertThatThrownBy(() -> categoryService.deleteCategory("99999"))
                .isInstanceOf(CategoryNotFoundException.class);
    }

}
