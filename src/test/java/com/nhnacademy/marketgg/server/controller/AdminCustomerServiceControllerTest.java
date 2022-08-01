package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminPostController.class)
class AdminCustomerServiceControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerServicePostService postService;

    private static final String DEFAULT_ADMIN_CUSTOMER_SERVICE = "/admin/customer-services";

    @Test
    @DisplayName("1:1 문의 단건 조회 - 관리자")
    void testRetrievePost() throws Exception {
        given(postService.retrievePost(anyLong())).willReturn(null);

        this.mockMvc.perform(get(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries/{inquiryId}", 1L))
                    .andExpect(status().isOk());

        then(postService).should().retrievePost(anyLong());
    }

    @Test
    @DisplayName("1:1 문의 목록 조회 - 관리자")
    void testRetrievePostList() throws Exception {
        given(postService.retrievePostList(any(Pageable.class))).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries"))
                    .andExpect(status().isOk());

        then(postService).should().retrievePostList(any(Pageable.class));
    }

    @Test
    @DisplayName("1:1 문의 상태 변경 - 관리자")
    void testUpdatePostStatus() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new PostStatusUpdateRequest());

        willDoNothing().given(postService).updateInquiryStatus(anyLong(), any(PostStatusUpdateRequest.class));

        this.mockMvc.perform(patch(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries/{inquiryId}", 1L)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(requestBody))
                    .andExpect(status().isOk());

        then(postService).should().updateInquiryStatus(anyLong(), any(PostStatusUpdateRequest.class));
    }

    @Test
    @DisplayName("1:1 문의 삭제 - 관리자")
    void testDeleteOtoInquiries() throws Exception {
        willDoNothing().given(postService).deletePost(anyLong());

        this.mockMvc.perform(delete(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries/{inquiryId}", 1L))
                    .andExpect(status().isOk());

        then(postService).should().deletePost(anyLong());
    }

    @Test
    @DisplayName("고객센터 게시글 사유 목록 조회")
    void testRetrieveAllReasonValues() throws Exception {
        this.mockMvc.perform(get(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/reasons"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(9)));
    }

}
