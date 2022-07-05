package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.CategoryRegisterRequest;
import com.nhnacademy.marketgg.server.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    ObjectMapper objectMapper = new ObjectMapper();
    String requestBody = "";

    @DisplayName("카테고리 등록 테스트")
    @Test
    void testCreateCategory() throws Exception {
        CategoryRegisterRequest categoryRequest = new CategoryRegisterRequest(0L, "채소", 1, "PROD");
        requestBody = objectMapper.writeValueAsString(categoryRequest);

        doNothing().when(categoryService).createCategory(any());

        this.mockMvc.perform(post("/admin/v1/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isCreated());

        verify(categoryService, times(1)).createCategory(any(categoryRequest.getClass()));
    }

}
