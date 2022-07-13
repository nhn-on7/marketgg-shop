package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.server.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CategoryService categoryService;

    @Test
    @DisplayName("카테고리 등록")
    void testCreateCategory() throws Exception {
        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest();
        String requestBody = objectMapper.writeValueAsString(categoryCreateRequest);

        doNothing().when(categoryService).createCategory(any(CategoryCreateRequest.class));

        mockMvc.perform(post("/admin/v1/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
               .andExpect(status().isCreated());

        verify(categoryService, times(1)).createCategory(any(CategoryCreateRequest.class));
    }

    @Test
    @DisplayName("카테고리 단건 조회")
    void testRetrieveCategory() throws Exception {
        when(categoryService.retrieveCategory(anyString())).thenReturn(null);

        this.mockMvc.perform(get("/admin/v1/categories/{categoryId}", "011"))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).retrieveCategory(anyString());
    }

    @Test
    @DisplayName("카테고리 목록 조회")
    void testRetrieveCategories() throws Exception {
        when(categoryService.retrieveCategories()).thenReturn(List.of());

        this.mockMvc.perform(get("/admin/v1/categories"))
                    .andExpect(status().isOk());

        verify(categoryService, times(1)).retrieveCategories();
    }


    @Test
    @DisplayName("카테고리 수정")
    void testUpdateCategory() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new CategoryUpdateRequest());

        doNothing().when(categoryService)
                   .updateCategory(anyString(), any(CategoryUpdateRequest.class));

        mockMvc.perform(put("/admin/v1/categories/{categoryId}", "001")
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

        this.mockMvc.perform(delete("/admin/v1/categories/{categoryId}", "001"))
                    .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategory(anyString());
    }

}
