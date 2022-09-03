package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.marketgg.server.aop.AspectUtils;
import com.nhnacademy.marketgg.server.controller.member.PointController;
import com.nhnacademy.marketgg.server.service.point.PointService;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AspectUtils.AUTH_ID, UUID.randomUUID().toString());
        httpHeaders.add(AspectUtils.WWW_AUTHENTICATE, "[\"ROLE_ADMIN\"]");


        given(pointService.retrievePointHistories(any(), any())).willReturn(Page.empty());
        mockMvc.perform(get(DEFAULT_MEMBER + "/points")
                                .param("page", "1")
                   .headers(httpHeaders))
               .andExpect(status().isOk());

        then(pointService).should(times(1)).retrievePointHistories(any(), any());
    }

}
