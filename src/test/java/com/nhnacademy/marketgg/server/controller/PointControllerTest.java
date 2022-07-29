package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.marketgg.server.service.PointService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PointController.class)
class PointControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointService pointService;

    private static final String DEFAULT_MEMBER = "/members";

    @Test
    @DisplayName("회원의 포인트 내역 목록 조회")
    void retrievePointHistory() throws Exception {
        given(pointService.retrievePointHistories(anyLong())).willReturn(List.of());
        mockMvc.perform(get(DEFAULT_MEMBER + "/{memberId}/points", 1L))
               .andExpect(status().isOk());
    }

}
