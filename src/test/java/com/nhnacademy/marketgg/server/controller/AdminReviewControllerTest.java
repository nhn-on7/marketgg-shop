package com.nhnacademy.marketgg.server.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.service.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminReviewController.class)
class AdminReviewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ReviewService reviewService;


    @Test
    @DisplayName("베스트 후기 선정 테스트")
    void testMakeBestReview() throws Exception {
        given(reviewService.makeBestReview(anyLong())).willReturn(new SingleResponse<>());

        this.mockMvc.perform(post("/admin/products/{productId}/review/{reviewId}/makeBest", 1L, 1L)
                                 .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        then(reviewService).should().makeBestReview(anyLong());
    }

}
