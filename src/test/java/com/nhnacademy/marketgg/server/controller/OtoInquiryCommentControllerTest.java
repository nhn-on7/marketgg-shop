package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.controller.customerservice.OtoInquiryCommentController;
import com.nhnacademy.marketgg.server.dto.request.CommentRequest;
import com.nhnacademy.marketgg.server.service.CustomerServiceCommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OtoInquiryCommentController.class)
public class OtoInquiryCommentControllerTest {

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

}
