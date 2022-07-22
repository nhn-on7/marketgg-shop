package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.service.MemberService;
import java.time.LocalDateTime;

import com.nhnacademy.marketgg.server.service.PointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @MockBean
    PointService pointService;

    @Test
    @DisplayName("GG 패스 갱신일자 확인")
    void checkPassUpdatedAt() throws Exception {
        when(memberService.retrievePassUpdatedAt(anyLong())).thenReturn(LocalDateTime.now());

        this.mockMvc.perform(get("/shop/v1/members/{memberId}/ggpass", 1L))
                    .andExpect(status().isOk());

        verify(memberService, times(1)).retrievePassUpdatedAt(anyLong());
    }

    @Test
    @DisplayName("GG 패스 가입")
    void joinPass() throws Exception {
        doNothing().when(memberService).subscribePass(anyLong());

        this.mockMvc.perform(post("/shop/v1/members/{memberId}/ggpass/subscribe", 1L))
                    .andExpect(status().isOk());

        verify(memberService, times(1)).subscribePass(anyLong());
    }

    @Test
    @DisplayName("GG 패스 해지")
    void withdrawPass() throws Exception {
        doNothing().when(memberService).withdrawPass(anyLong());

        this.mockMvc.perform(post("/shop/v1/members/{memberId}/ggpass/withdraw", 1L))
                    .andExpect(status().isOk());

        verify(memberService, times(1)).withdrawPass(anyLong());
    }

}