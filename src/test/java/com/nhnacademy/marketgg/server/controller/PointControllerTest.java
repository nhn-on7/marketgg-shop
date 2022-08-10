package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.marketgg.server.aop.RoleCheckAspect;
import com.nhnacademy.marketgg.server.controller.member.PointController;
import com.nhnacademy.marketgg.server.service.point.PointService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PointController.class)
@Import({
        RoleCheckAspect.class
})
class PointControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointService pointService;

    private static final String DEFAULT_MEMBER = "/members";

    @Test
    @DisplayName("회원의 포인트 내역 목록 조회")
    void retrievePointHistory() throws Exception {
        given(pointService.retrievePointHistories(any())).willReturn(List.of());
        mockMvc.perform(get(DEFAULT_MEMBER + "/points"))
               .andExpect(status().isOk());

        then(pointService).should(times(1)).retrievePointHistories(any());
    }

}
