package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.CommentRequest;
import com.nhnacademy.marketgg.server.dto.response.CustomerServiceCommentDto;
import com.nhnacademy.marketgg.server.service.CustomerServiceCommentService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerServiceCommentController.class)
public class CustomerServiceCommentControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerServiceCommentService customerServiceCommentService;

    private static final String DEFAULT_CS_COMMENT = "/customer-services/oto-inquiries";

    @Test
    @DisplayName("고객센터 게시글에 댓글 등록")
    void testCreateComment() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new CommentRequest());

        willDoNothing().given(customerServiceCommentService)
                       .createComment(anyLong(), anyLong(), any(CommentRequest.class));

        this.mockMvc.perform(post(DEFAULT_CS_COMMENT + "/{inquiryId}/members/{memberId}/comments",1L, 1L)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(requestBody))
                    .andExpect(status().isCreated());

        then(customerServiceCommentService).should()
                                           .createComment(anyLong(), anyLong(), any(CommentRequest.class));
    }

    @Test
    @DisplayName("댓글 단건 조회")
    void testRetrieveComment() throws Exception {
        given(customerServiceCommentService.retrieveComment(anyLong())).willReturn(null);

        this.mockMvc.perform(get(DEFAULT_CS_COMMENT + "/comments/{commentId}", 1L))
                    .andExpect(status().isOk());

        then(customerServiceCommentService).should().retrieveComment(anyLong());
    }

    @Test
    @DisplayName("고객센터 게시글의 댓글 목록 조회")
    void testRetrieveInquiryComment() throws Exception {
        given(customerServiceCommentService.retrieveCommentsByInquiry(anyLong())).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_CS_COMMENT + "/{inquiryId}/comments", 1L))
                    .andExpect(status().isOk());

        then(customerServiceCommentService).should().retrieveCommentsByInquiry(anyLong());
    }
}
