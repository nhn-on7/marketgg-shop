package com.nhnacademy.marketgg.server.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.LabelCreateRequest;
import com.nhnacademy.marketgg.server.service.LabelService;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        LabelCreateRequest labelCreateRequest = new LabelCreateRequest("hello");

        doNothing().when(labelService).createLabel(any(LabelCreateRequest.class));

        this.mockMvc.perform(post("/admin/v1/labels")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(labelCreateRequest)))
                    .andExpect(status().isCreated());

        verify(labelService, times(1)).createLabel(any(labelCreateRequest.getClass()));
    }

    @Test
    @DisplayName("라벨 삭제")
    void deleteLabel() throws Exception {
        doNothing().when(labelService).deleteLabel(anyLong());

        this.mockMvc.perform(delete("/admin/v1/labels/{labelId}", 1L))
                    .andExpect(status().isOk());

        verify(labelService, times(1)).deleteLabel(1L);
    }

}