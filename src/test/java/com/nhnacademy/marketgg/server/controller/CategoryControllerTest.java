package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.CategoryRequest;
import com.nhnacademy.marketgg.server.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    @DisplayName("카테고리 등록 테스트")
    void testCreateCategory() throws Exception {
        CategoryRequest categoryRequest = CategoryRequest.of();

        doNothing().when(categoryService).createCategory(any());

        this.mockMvc.perform(post("/admin/v1/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(categoryRequest)))
                    .andExpect(status().isCreated());

        verify(categoryService, times(1)).createCategory(any(categoryRequest.getClass()));
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    void testUpdateCategory() throws Exception {
        CategoryRequest categoryRequest = CategoryRequest.of();

        doNothing().when(categoryService).updateCategory(anyLong(), any());

        this.mockMvc.perform(put("/admin/v1/categories/{category-id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(categoryRequest)))
                    .andExpect(status().isOk());

        verify(categoryService, times(1))
                .updateCategory(anyLong(), any(categoryRequest.getClass()));
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void testDeleteCategory() throws Exception {
        Long categoryId = 1L;

        doNothing().when(categoryService).deleteCategory(anyLong());

        this.mockMvc.perform(delete("/admin/v1/categories/{category-id}", categoryId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.valueOf(categoryId)))
                    .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategory(anyLong());
    }

}
