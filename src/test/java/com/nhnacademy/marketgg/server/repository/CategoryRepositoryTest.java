package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.repository.categorization.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategorizationRepository categorizationRepository;

    private static CategoryCreateRequest categoryCreateRequest;
    private static CategorizationCreateRequest categorizationCreateRequest;

    @BeforeAll
    static void beforeAll() {
        categoryCreateRequest = new CategoryCreateRequest();
        categorizationCreateRequest = new CategorizationCreateRequest();

        ReflectionTestUtils.setField(categoryCreateRequest, "categoryCode", "101");
        ReflectionTestUtils.setField(categoryCreateRequest, "categorizationCode", "100");
        ReflectionTestUtils.setField(categoryCreateRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryCreateRequest, "sequence", 1);

        ReflectionTestUtils.setField(categorizationCreateRequest, "categorizationCode", "100");
        ReflectionTestUtils.setField(categorizationCreateRequest, "name", "상품");
        ReflectionTestUtils.setField(categorizationCreateRequest, "alias", "PRODUCT");
    }

    @Test
    @DisplayName("카테고리 코드로 조회")
    void testRetrieveByCategoryCode() {
        Categorization categorization = new Categorization(categorizationCreateRequest);
        Category category = new Category(categoryCreateRequest, categorization);

        categorizationRepository.save(categorization);
        categoryRepository.save(category);

        CategoryRetrieveResponse result = categoryRepository.findByCode(category.getId());

        assertThat(result.getCategorizationName()).isEqualTo(categorization.getName());
    }

    @Test
    @DisplayName("카테고리 분류표 코드로 조회")
    void testRetrieveByCategorizationCode() {
        Categorization categorization = new Categorization(categorizationCreateRequest);
        Category category = new Category(categoryCreateRequest, categorization);

        categorizationRepository.save(categorization);
        categoryRepository.save(category);

        List<CategoryRetrieveResponse> results = categoryRepository.findByCategorizationCode(categorization.getId());

        assertThat(results).hasSize(1);
    }

    @Test
    @DisplayName("모든 카테고리 목록 조회")
    void testRetrieveAllCategories() {
        Categorization categorization = new Categorization(categorizationCreateRequest);
        Category category = new Category(categoryCreateRequest, categorization);

        categorizationRepository.save(categorization);
        categoryRepository.save(category);

        List<CategoryRetrieveResponse> results = categoryRepository.findAllCategories();

        assertThat(results).hasSize(1);
    }

}
