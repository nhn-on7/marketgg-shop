package com.nhnacademy.marketgg.server.controller.admin;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.aop.AspectUtils;
import com.nhnacademy.marketgg.server.dto.request.label.LabelCreateRequest;
import com.nhnacademy.marketgg.server.service.label.LabelService;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminLabelController.class)
class AdminLabelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LabelService labelService;

    private static final String DEFAULT_LABEL = "/admin/labels";

    HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add(AspectUtils.AUTH_ID, UUID.randomUUID().toString());
        httpHeaders.add(AspectUtils.WWW_AUTHENTICATE, "[\"ROLE_ADMIN\"]");
    }

    @Test
    @DisplayName("라벨 등록")
    void createLabel() throws Exception {
        LabelCreateRequest labelCreateRequest = new LabelCreateRequest();
        ReflectionTestUtils.setField(labelCreateRequest, "name", "hello");
        String requestBody = objectMapper.writeValueAsString(labelCreateRequest);

        willDoNothing().given(labelService).createLabel(any(LabelCreateRequest.class));

        this.mockMvc.perform(post(DEFAULT_LABEL)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                    .andExpect(status().isCreated());

        then(labelService).should(times(1)).createLabel(any(labelCreateRequest.getClass()));
    }

    @Test
    @DisplayName("라벨 조회")
    void retrieveLabels() throws Exception {

        given(labelService.retrieveLabels()).willReturn(new ArrayList<>());

        this.mockMvc.perform(get(DEFAULT_LABEL).headers(httpHeaders))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

        then(labelService).should(times(1)).retrieveLabels();
    }

    @Test
    @DisplayName("라벨 삭제")
    void deleteLabel() throws Exception {
        willDoNothing().given(labelService).deleteLabel(anyLong());

        this.mockMvc.perform(delete(DEFAULT_LABEL + "/{labelId}", 1L).headers(httpHeaders))
                    .andExpect(status().isOk());

        then(labelService).should(times(1)).deleteLabel(1L);
    }

}
