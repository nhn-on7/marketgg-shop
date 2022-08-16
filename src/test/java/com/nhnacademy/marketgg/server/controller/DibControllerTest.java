package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.aop.RoleCheckAspect;
import com.nhnacademy.marketgg.server.controller.member.DibController;
import com.nhnacademy.marketgg.server.service.dib.DibService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DibController.class)
@Import({
        RoleCheckAspect.class
})
class DibControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DibService dibService;

    private static final String DEFAULT_DIB = "/members/dibs";

    @Test
    @DisplayName("찜 등록")
    void testCreateDib() throws Exception {
        willDoNothing().given(dibService).createDib(any(), anyLong());

        mockMvc.perform(post(DEFAULT_DIB + "/{productId}", 1L))
               .andExpect(status().isCreated());

        then(dibService).should(times(1)).createDib(any(), anyLong());
    }

    @Test
    @DisplayName("찜 조회")
    void testRetrieveDibs() throws Exception {
        given(dibService.retrieveDibs(any())).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_DIB))
                    .andExpect(status().isOk());

        then(dibService).should(times(1)).retrieveDibs(any());
    }

    @Test
    @DisplayName("찜 삭제")
    void testDeleteDib() throws Exception {
        willDoNothing().given(dibService).deleteDib(any(), anyLong());

        mockMvc.perform(delete(DEFAULT_DIB + "/{productId}", 1L))
               .andExpect(status().isOk());

        then(dibService).should(times(1)).deleteDib(any(), anyLong());
    }

}
