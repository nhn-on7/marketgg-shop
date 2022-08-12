package com.nhnacademy.marketgg.server.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.aop.AspectUtils;
import com.nhnacademy.marketgg.server.aop.RoleCheckAspect;
import com.nhnacademy.marketgg.server.controller.admin.AdminCategorizationController;
import com.nhnacademy.marketgg.server.service.category.CategorizationService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AdminCategorizationController.class)
@Import({
        RoleCheckAspect.class
})
class AdminCategorizationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CategorizationService categorizationService;

    HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add(AspectUtils.AUTH_ID, UUID.randomUUID().toString());
        httpHeaders.add(AspectUtils.WWW_AUTHENTICATE, "[\"ROLE_ADMIN\"]");
    }

    @Test
    @DisplayName("카테고리 분류표 조회")
    void retrieveCategorization() throws Exception {
        when(categorizationService.retrieveCategorizations()).thenReturn(List.of());

        this.mockMvc.perform(get("/admin/categorizations")
                .headers(httpHeaders))
                .andExpect(status().isOk());

        verify(categorizationService, times(1)).retrieveCategorizations();
    }

}
