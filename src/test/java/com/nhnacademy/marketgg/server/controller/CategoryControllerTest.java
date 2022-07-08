package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    @DisplayName("카테고리 목록 조회 테스트")
    void testRetrieveCategories() throws Exception {
        when(categoryService.retrieveCategories()).thenReturn(List.of());

        this.mockMvc.perform(get("/admin/v1/categories"))
                    .andExpect(status().isOk());

        verify(categoryService, times(1)).retrieveCategories();
    }

}
