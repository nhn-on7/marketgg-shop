package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.nhnacademy.marketgg.server.dto.CategoryUpdateRequest;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
    @DisplayName("카테고리 목록 조회 테스트")
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
                   .updateCategory(anyLong(), any(CategoryUpdateRequest.class));

        mockMvc.perform(put("/admin/v1/categories/{categoryId}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
               .andExpect(status().isOk());

        verify(categoryService, times(1)).updateCategory(anyLong(),
                                                         any(CategoryUpdateRequest.class));
    }  
    
    @Test
    @DisplayName("카테고리 삭제 테스트")
    void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(anyLong());

        this.mockMvc.perform(delete("/admin/v1/categories/{categoryId}", 1L))
                    .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategory(anyLong());
    }
    
}
