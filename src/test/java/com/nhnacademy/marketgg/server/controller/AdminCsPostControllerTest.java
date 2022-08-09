package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.aop.RoleCheckAspect;
import com.nhnacademy.marketgg.server.controller.admin.AdminCsPostController;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.service.post.PostService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminCsPostController.class)
@Import({
    RoleCheckAspect.class
})
class AdminCsPostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    private PostRequest postRequest;
    private PostResponse postResponse;
    private PostStatusUpdateRequest updateRequest;

    private static final String DEFAULT_ADMIN_POST = "/admin/customer-services";

    @BeforeEach
    void setUp() {
        postRequest = new PostRequest();
        postResponse = new PostResponse(1L, "702", "hello", "배송", "종료", LocalDateTime.now());
        updateRequest = new PostStatusUpdateRequest();
    }

    @Test
    @DisplayName("옵션에 따른 게시글 검색")
    void testSearchPostListForOption() throws Exception {
        given(postService.searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString())).willReturn(
            List.of(postResponse));

        this.mockMvc.perform(
                get(DEFAULT_ADMIN_POST + "/categories/{categoryId}/options/{optionType}/search", "702", "reason")
                    .param("option", "배송")
                    .param("keyword", "hi")
                    .param("page", "0"))
                    .andExpect(status().isOk());

        then(postService).should(times(1))
                         .searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString());
    }

    @Test
    @DisplayName("게시글 수정")
    void testUpdatePost() throws Exception {
        willDoNothing().given(postService).updatePost(anyString(), anyLong(), any(PostRequest.class));

        this.mockMvc.perform(put(DEFAULT_ADMIN_POST + "/categories/{categoryId}/{postId}", "701", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequest)))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).updatePost(anyString(), anyLong(), any(PostRequest.class));
    }

    @Test
    @DisplayName("1:1 문의 상태 목록 조회")
    void testRetrieveStatusList() throws Exception {
        this.mockMvc.perform(get(DEFAULT_ADMIN_POST + "/status"))
                    .andExpect(status().isOk());
    }

    @Test
    @DisplayName("1:1 문의 상태 변경")
    void testUpdateInquiryStatus() throws Exception {
        willDoNothing().given(postService).updateOtoInquiryStatus(anyLong(), any(PostStatusUpdateRequest.class));

        this.mockMvc.perform(patch(DEFAULT_ADMIN_POST + "/{postId}/status", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).updateOtoInquiryStatus(anyLong(), any(PostStatusUpdateRequest.class));
    }

}
