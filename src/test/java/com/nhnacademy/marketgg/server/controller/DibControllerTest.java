package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.DibCreateRequest;
import com.nhnacademy.marketgg.server.service.DibService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DibController.class)
public class DibControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DibService dibService;

    @Test
    @DisplayName("찜 등록")
    void testCreateDib() throws Exception {
        DibCreateRequest dibCreateRequest = new DibCreateRequest();
        String requestBody = objectMapper.writeValueAsString(dibCreateRequest);

        doNothing().when(dibService).createDib(any(DibCreateRequest.class));

        mockMvc.perform(post("/shop/v1/dibs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
               .andExpect(status().isCreated());

        verify(dibService, times(1)).createDib(any(DibCreateRequest.class));
    }

}
