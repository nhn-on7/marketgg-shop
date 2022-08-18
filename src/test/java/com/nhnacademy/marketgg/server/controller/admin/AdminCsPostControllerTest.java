package com.nhnacademy.marketgg.server.controller.admin;

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
import com.nhnacademy.marketgg.server.aop.AspectUtils;
import com.nhnacademy.marketgg.server.aop.RoleCheckAspect;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.service.post.PostService;
import java.time.LocalDateTime;
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
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
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

    HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add(AspectUtils.AUTH_ID, UUID.randomUUID().toString());
        httpHeaders.add(AspectUtils.WWW_AUTHENTICATE, "[\"ROLE_ADMIN\"]");

        postRequest = new PostRequest();
        postResponse = new PostResponse(1L, "702", "hello", "배송", "종료", LocalDateTime.now());
        updateRequest = new PostStatusUpdateRequest();
        ReflectionTestUtils.setField(postRequest, "categoryCode", "702");
        ReflectionTestUtils.setField(postRequest, "title", "hello");
        ReflectionTestUtils.setField(postRequest, "content", "hi");
        ReflectionTestUtils.setField(postRequest, "reason", "환불");
        ReflectionTestUtils.setField(updateRequest, "status", "hello");
    }

    @Test
    @DisplayName("옵션에 따른 게시글 검색")
    void testSearchPostListForOption() throws Exception {
        given(postService.searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString())).willReturn(
                List.of(postResponse));

        this.mockMvc.perform(
                    post(DEFAULT_ADMIN_POST + "/categories/{categoryId}/options/{optionType}/search", "702", "reason")
                            .headers(httpHeaders)
                            .param("categoryCode", "702")
                            .param("option", "배송")
                            .param("keyword", "hi")
                            .param("page", "0")
                            .param("size", "10"))
                    .andExpect(status().isOk());

        then(postService).should(times(1))
                         .searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString());
    }

    @Test
    @DisplayName("게시글 수정")
    void testUpdatePost() throws Exception {
        willDoNothing().given(postService).updatePost(anyString(), anyLong(), any(PostRequest.class));

        this.mockMvc.perform(put(DEFAULT_ADMIN_POST + "/categories/{categoryId}/{postId}", "701", 1L)
                                     .headers(httpHeaders)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(postRequest)))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).updatePost(anyString(), anyLong(), any(PostRequest.class));
    }

    @Test
    @DisplayName("1:1 문의 상태 목록 조회")
    void testRetrieveStatusList() throws Exception {
        this.mockMvc.perform(get(DEFAULT_ADMIN_POST + "/status")
                                     .headers(httpHeaders))
                    .andExpect(status().isOk());
    }

    @Test
    @DisplayName("1:1 문의 상태 변경")
    void testUpdateInquiryStatus() throws Exception {
        willDoNothing().given(postService).updateOtoInquiryStatus(anyLong(), any(PostStatusUpdateRequest.class));

        this.mockMvc.perform(patch(DEFAULT_ADMIN_POST + "/{postId}/status", 1L)
                                     .headers(httpHeaders)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(updateRequest)))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).updateOtoInquiryStatus(anyLong(), any(PostStatusUpdateRequest.class));
    }

}
