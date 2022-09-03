package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.controller.customerservice.CsPostController;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostRequest;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForDetail;
import com.nhnacademy.marketgg.server.elastic.request.SearchRequest;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CsPostController.class)
class CsPostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    private PostRequest postRequest;
    private PostResponse postResponse;
    private PostResponseForDetail postResponseForDetail;

    private static final String DEFAULT_POST = "/customer-services";

    HttpHeaders headers;

    @BeforeEach
    void setUp() {
        postRequest = new PostRequest();
        postResponse = new PostResponse(1L, "702", "hello", "배송", "종료", LocalDateTime.now());
        postResponseForDetail = new PostResponseForDetail(1L, "702", "hello", "hi", "배송", "종료",
            LocalDateTime.now(), LocalDateTime.now(), List.of());

        String uuid = UUID.randomUUID().toString();
        String role = "[\"ROLE_USER\"]";

        headers = new HttpHeaders();
        headers.set("AUTH-ID", uuid);
        headers.set("WWW-Authenticate", role);

        ReflectionTestUtils.setField(postRequest, "categoryCode", "702");
        ReflectionTestUtils.setField(postRequest, "title", "hello");
        ReflectionTestUtils.setField(postRequest, "content", "hi");
        ReflectionTestUtils.setField(postRequest, "reason", "환불");
    }

    @Test
    @DisplayName("게시글 등록")
    void testCreatePost() throws Exception {
        willDoNothing().given(postService).createPost(any(PostRequest.class), any(MemberInfo.class));

        this.mockMvc.perform(post(DEFAULT_POST)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequest)))
                    .andExpect(status().isCreated());

        then(postService).should(times(1)).createPost(any(PostRequest.class), any(MemberInfo.class));
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void testRetrievePostList() throws Exception {
        given(postService.retrievePostList(anyString(), anyInt(), any(MemberInfo.class))).willReturn(
            List.of(postResponse));

        this.mockMvc.perform(get(DEFAULT_POST + "/categories/{categoryId}", "702")
                .headers(headers)
                .param("page", "0"))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).retrievePostList(anyString(), anyInt(), any(MemberInfo.class));
    }

    @Test
    @DisplayName("게시글 상세 조회")
    void testRetrievePost() throws Exception {
        given(postService.retrievePost(anyLong(), any(MemberInfo.class))).willReturn(postResponseForDetail);

        this.mockMvc.perform(get(DEFAULT_POST + "/{postId}", 1L)
                .headers(headers))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).retrievePost(anyLong(), any(MemberInfo.class));
    }

    @Test
    @DisplayName("카테고리 별 게시글 검색")
    void testSearchPostListForCategory() throws Exception {
        given(postService.searchForCategory(any(SearchRequest.class), any(MemberInfo.class))).willReturn(
            List.of(postResponse));

        SearchRequest searchRequest = new SearchRequest();

        ReflectionTestUtils.setField(searchRequest, "categoryCode", "703");
        ReflectionTestUtils.setField(searchRequest, "keyword", "hi");
        ReflectionTestUtils.setField(searchRequest, "page", 0);
        ReflectionTestUtils.setField(searchRequest, "size", 10);

        this.mockMvc.perform(post(DEFAULT_POST + "/categories/{categoryId}/search", "703")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequest)))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).searchForCategory(any(SearchRequest.class), any(MemberInfo.class));
    }

    @Test
    @DisplayName("게시글 삭제")
    void testDeletePost() throws Exception {
        willDoNothing().given(postService).deletePost(anyString(), anyLong(), any(MemberInfo.class));

        this.mockMvc.perform(delete(DEFAULT_POST + "/categories/{categoryId}/{postId}", "702", 1L)
                .headers(headers))
                    .andExpect(status().isNoContent());

        then(postService).should(times(1)).deletePost(anyString(), anyLong(), any(MemberInfo.class));
    }

    @Test
    @DisplayName("1:1 문의 사유 목록 조회")
    void testRetrieveReasonList() throws Exception {
        this.mockMvc.perform(get(DEFAULT_POST + "/reasons")
                .headers(headers))
                    .andExpect(status().isOk());
    }

    @Test
    @DisplayName("1:1 문의 상태 목록 조회")
    void testRetrieveStatusList() throws Exception {
        this.mockMvc.perform(get(DEFAULT_POST + "/status")
                .headers(headers))
                    .andExpect(status().isOk());
    }


}
