package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.LabelDto;
import com.nhnacademy.marketgg.server.service.LabelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LabelController.class)
class LabelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private LabelService labelService;

    @Test
    @DisplayName("라벨 조회")
    void retrieveLabels() throws Exception {

        when(labelService.retrieveLabels()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/admin/v1/labels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(labelService, times(1)).retrieveLabels();
    }

    @Test
    @DisplayName("라벨 등록")
    void createLabel() throws Exception {
        LabelDto labelDto = new LabelDto("hello");

        doNothing().when(labelService).createLabel(any());

        this.mockMvc.perform(post("/admin/v1/labels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(labelDto)))
                .andExpect(status().isCreated());

        verify(labelService, times(1)).createLabel(any(labelDto.getClass()));
    }

    @Test
    @DisplayName("라벨 삭제")
    void deleteLabel() throws Exception {
        doNothing().when(labelService).deleteLabel(anyLong());

        this.mockMvc.perform(delete("/admin/v1/labels/{label-id}", 1L))
                .andExpect(status().isOk());

        verify(labelService, times(1)).deleteLabel(1L);
    }

}