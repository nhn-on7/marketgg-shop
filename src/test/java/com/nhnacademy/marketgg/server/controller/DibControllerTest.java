package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.service.DibService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DibController.class)
class DibControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DibService dibService;

    @Test
    @DisplayName("찜 등록")
    void testCreateDib() throws Exception {
        doNothing().when(dibService).createDib(anyLong(), anyLong());

        mockMvc.perform(post("/members/1/dibs/1"))
               .andExpect(status().isCreated());

        verify(dibService, times(1)).createDib(anyLong(), anyLong());
    }

    @Test
    @DisplayName("찜 조회")
    void testRetrieveDibs() throws Exception {
        when(dibService.retrieveDibs(1L)).thenReturn(List.of());

        this.mockMvc.perform(get("/members/1/dibs"))
                    .andExpect(status().isOk());

        verify(dibService, times(1)).retrieveDibs(anyLong());
    }

    @Test
    @DisplayName("찜 삭제")
    void testDeleteDib() throws Exception {
        doNothing().when(dibService).deleteDib(anyLong(), anyLong());

        mockMvc.perform(delete("/members/1/dibs/1"))
               .andExpect(status().isOk());

        verify(dibService, times(1)).deleteDib(anyLong(), anyLong());
    }

}
