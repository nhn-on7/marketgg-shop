package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.repository.CategoryRepository;
import com.nhnacademy.marketgg.server.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class DefaultCategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 목록 조회")
    void testRetrieveCategories() {
        when(categoryRepository.findAllCategories()).thenReturn(List.of(new CategoryRetrieveResponse()));

        List<CategoryRetrieveResponse> categoryResponses = categoryService.retrieveCategories();

        assertThat(categoryResponses).hasSize(1);
    }

}
