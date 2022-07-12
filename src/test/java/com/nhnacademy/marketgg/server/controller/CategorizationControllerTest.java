package com.nhnacademy.marketgg.server.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.server.service.CategorizationService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(CategorizationController.class)
class CategorizationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CategorizationService categorizationService;

    @Test
    @DisplayName("카테고리 분류표 조회")
    void retrieveCategorization() throws Exception {
        when(categorizationService.retrieveCategorizations()).thenReturn(List.of());

        this.mockMvc.perform(get("/admin/v1/categorizations"))
                .andExpect(status().isOk());

        verify(categorizationService, times(1)).retrieveCategorizations();
    }

}