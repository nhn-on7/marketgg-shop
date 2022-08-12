package com.nhnacademy.marketgg.server.controller.admin;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.marketgg.server.aop.AspectUtils;
import com.nhnacademy.marketgg.server.aop.RoleCheckAspect;
import com.nhnacademy.marketgg.server.service.point.PointService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminPointController.class)
@Import({
    RoleCheckAspect.class
})
class AdminPointControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PointService pointService;

    private static final String DEFAULT_ADMIN = "/admin";

    HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add(AspectUtils.AUTH_ID, UUID.randomUUID().toString());
        httpHeaders.add(AspectUtils.WWW_AUTHENTICATE, "[\"ROLE_ADMIN\"]");
    }

    @Test
    @DisplayName("관리자의 사용자 전체 포인트 내역 조회")
    void testAdminRetrievePointHistory() throws Exception {
        given(pointService.adminRetrievePointHistories()).willReturn(List.of());
        mockMvc.perform(get(DEFAULT_ADMIN + "/points")
                   .headers(httpHeaders))
               .andExpect(status().isOk());
    }

}
