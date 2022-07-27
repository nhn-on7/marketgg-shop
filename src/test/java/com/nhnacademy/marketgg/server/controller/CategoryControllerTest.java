package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.server.service.CategoryService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CategoryService categoryService;

    // @MockBean
    // CategoryRepository categoryRepository;
    //
    // @MockBean
    // CategorizationRepository categorizationRepository;

    private static final String DEFAULT_CATEGORY = "/admin/categories";

    private CategoryCreateRequest categoryCreateRequest;
    private CategoryUpdateRequest categoryUpdateRequest;
    private CategorizationCreateRequest categorizationCreateRequest;

    @BeforeEach
    void setUp() {
        categoryCreateRequest = new CategoryCreateRequest();
        categoryUpdateRequest = new CategoryUpdateRequest();
        categorizationCreateRequest = new CategorizationCreateRequest();

        ReflectionTestUtils.setField(categoryCreateRequest, "categoryCode", "101");
        ReflectionTestUtils.setField(categoryCreateRequest, "categorizationCode", "100");
        ReflectionTestUtils.setField(categoryCreateRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryCreateRequest, "sequence", 1);

        ReflectionTestUtils.setField(categoryUpdateRequest, "categoryCode", "101");
        ReflectionTestUtils.setField(categoryUpdateRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryUpdateRequest, "sequence", 1);

        ReflectionTestUtils.setField(categorizationCreateRequest, "categorizationCode", "100");
        ReflectionTestUtils.setField(categorizationCreateRequest, "name", "상품");
        ReflectionTestUtils.setField(categorizationCreateRequest, "alias", "PRODUCT");
    }

    @Test
    @DisplayName("카테고리 등록")
    void testCreateCategory() throws Exception {
        String requestBody = objectMapper.writeValueAsString(categoryCreateRequest);

        doNothing().when(categoryService).createCategory(any(CategoryCreateRequest.class));

        mockMvc.perform(post(DEFAULT_CATEGORY)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
               .andExpect(status().isCreated());

        verify(categoryService, times(1)).createCategory(any(CategoryCreateRequest.class));
    }

    @Test
    @DisplayName("카테고리 단건 조회")
    void testRetrieveCategory() throws Exception {
        when(categoryService.retrieveCategory(anyString())).thenReturn(null);

        this.mockMvc.perform(get(DEFAULT_CATEGORY + "/{categoryId}", "011"))
                    .andExpect(status().isOk());

        verify(categoryService, times(1)).retrieveCategory(anyString());
    }

    @Test
    @DisplayName("카테고리 분류표별 카테고리 목록 조회")
    void testRetrieveCategoriesByCategorization() throws Exception {
        given(categoryService.retrieveCategoriesByCategorization(anyString())).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_CATEGORY + "/categorizations/{categorizationId}", "100"))
                    .andExpect(status().isOk());

        then(categoryService).should().retrieveCategoriesByCategorization(anyString());
    }

    @Test
    @DisplayName("카테고리 목록 조회")
    void testRetrieveCategories() throws Exception {
        when(categoryService.retrieveCategories()).thenReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_CATEGORY))
                    .andExpect(status().isOk());

        verify(categoryService, times(1)).retrieveCategories();
    }


    @Test
    @DisplayName("카테고리 수정")
    void testUpdateCategory() throws Exception {
        String requestBody = objectMapper.writeValueAsString(categoryUpdateRequest);

        doNothing().when(categoryService)
                   .updateCategory(anyString(), any(CategoryUpdateRequest.class));

        mockMvc.perform(put(DEFAULT_CATEGORY + "/{categoryId}", "001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
               .andExpect(status().isOk());

        verify(categoryService, times(1)).updateCategory(anyString(),
                                                         any(CategoryUpdateRequest.class));
    }

    @Test
    @DisplayName("카테고리 삭제")
    void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(anyString());

        this.mockMvc.perform(delete(DEFAULT_CATEGORY + "/{categoryId}", "001"))
                    .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategory(anyString());
    }

}
