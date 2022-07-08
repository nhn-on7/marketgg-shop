package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

}
