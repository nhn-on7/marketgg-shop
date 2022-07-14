package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.DibCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.DibDeleteRequest;
import com.nhnacademy.marketgg.server.service.DibService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

    // @Test
    // @DisplayName("찜 조회")
    // void testRetrieveDibs() throws Exception {
    //     doNothing().when()
    //     when(dibService.retrieveDibs(anyLong())).thenReturn(List.of());
    //
    //     this.mockMvc.perform(get("/shop/v1/dips/" + anyLong()))
    //             .andExpect(status().isOk());
    //
    //     verify(dibService, times(1)).retrieveDibs(anyLong());
    // }

    @Test
    @DisplayName("찜 삭제")
    void testDeleteDib() throws Exception {
        DibDeleteRequest dibDeleteRequest = new DibDeleteRequest();
        String requestBody = objectMapper.writeValueAsString(dibDeleteRequest);

        doNothing().when(dibService).deleteDib(any(DibDeleteRequest.class));

        mockMvc.perform(delete("/shop/v1/dibs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
               .andExpect(status().isOk());

        verify(dibService, times(1)).deleteDib(any(DibDeleteRequest.class));
    }

}
