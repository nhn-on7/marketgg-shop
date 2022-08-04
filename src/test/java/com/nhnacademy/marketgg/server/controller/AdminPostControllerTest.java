package com.nhnacademy.marketgg.server.controller;

import static com.nhnacademy.marketgg.server.aop.AspectUtils.AUTH_ID;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.aop.AuthInjectAspect;
import com.nhnacademy.marketgg.server.aop.MemberInfoAspect;
import com.nhnacademy.marketgg.server.aop.RoleCheckAspect;
import com.nhnacademy.marketgg.server.aop.UuidAspect;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.PostService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@Transactional
@SpringBootTest
@ActiveProfiles({"testdb", "common"})
@Import({
    RoleCheckAspect.class,
    AuthInjectAspect.class,
    UuidAspect.class,
    MemberInfoAspect.class
})
class AdminPostControllerTest {

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    @MockBean
    MemberRepository memberRepository;

    HttpHeaders headers;

    String uuid;
    Long memberId = 1L;

    private static final String DEFAULT_ADMIN_CUSTOMER_SERVICE = "/admin/customer-services";

    @BeforeEach
    void setUp(WebApplicationContext wac) throws JsonProcessingException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                                      .alwaysDo(print())
                                      .build();

        uuid = UUID.randomUUID().toString();
        MemberInfo memberInfo = Dummy.getDummyMemberInfo(memberId, new Cart());
        given(memberRepository.findMemberInfoByUuid(uuid)).willReturn(Optional.of(memberInfo));

        String roles = objectMapper.writeValueAsString(Collections.singletonList(Role.ROLE_ADMIN));
        headers = new HttpHeaders();
        headers.set(AUTH_ID, uuid);
        headers.set(HttpHeaders.WWW_AUTHENTICATE, roles);
    }

    @Test
    @DisplayName("고객센터 게시글 등록 - 관리자")
    void testCreatePost() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new PostRequest());

        willDoNothing().given(postService).createPost(anyLong(), any(PostRequest.class));

        this.mockMvc.perform(post(DEFAULT_ADMIN_CUSTOMER_SERVICE)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                    .andExpect(status().isCreated());

        then(postService).should(times(1)).createPost(anyLong(), any(PostRequest.class));
    }

    @Test
    @DisplayName("고객센터 게시글 단건 조회 - 관리자")
    void testRetrievePost() throws Exception {
        given(postService.retrievePost(anyLong())).willReturn(Dummy.getDummyPostResponseForDetail());

        this.mockMvc.perform(get(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/{boardNo}", 1L)
                .headers(headers))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).retrievePost(anyLong());
    }

    @Test
    @DisplayName("1:1 문의 단건 조회 - 관리자")
    void testRetrieveOtoInquiryPost() throws Exception {
        given(postService.retrieveOtoInquiryPost(anyLong())).willReturn(Dummy.getDummyPostResponseForOtoInquiry());

        this.mockMvc.perform(get(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries/{boardNo}", 1L)
                .headers(headers))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).retrieveOtoInquiryPost(anyLong());
    }

    @Test
    @DisplayName("카테고리 번호에 따른 게시글 목록 조회 - 관리자")
    void testRetrievePostList() throws Exception {
        given(postService.retrievePostList(anyString(), anyInt())).willReturn(List.of(Dummy.getDummyPostResponse()));

        this.mockMvc.perform(get(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/categories/{categoryCode}", "702")
                .headers(headers)
                .param("page", "1"))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).retrievePostList(anyString(), anyInt());
    }

    @Test
    @DisplayName("지정한 게시판 타입의 Reason 옵션으로 검색한 결과 조회 - 관리자")
    void testSearchPostListForReason() throws Exception {
        String requestBody = objectMapper.writeValueAsString(Dummy.getSearchRequest());

        given(postService.searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString()))
            .willReturn(List.of(Dummy.getSearchBoardResponse()));

        this.mockMvc.perform(post(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/categories/{categoryCode}/search/reason", "701")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .param("option", "취소/환불/교환"))
                    .andExpect(status().isOk());

        then(postService).should(times(1))
                         .searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString());
    }

    @Test
    @DisplayName("지정한 게시판 타입의 Status 옵션으로 검색한 결과 조회 - 관리자")
    void testPostListForStatus() throws Exception {
        String requestBody = objectMapper.writeValueAsString(Dummy.getSearchRequest());

        given(postService.searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString()))
            .willReturn(List.of(Dummy.getSearchBoardResponse()));

        this.mockMvc.perform(post(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/categories/{categoryCode}/search/status", "701", "미답변")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .param("option", "종료"))
                    .andExpect(status().isOk());

        then(postService).should(times(1))
                         .searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString());
    }

    @Test
    @DisplayName("고객센터 게시글 수정")
    void testUpdatePost() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new PostRequest());

        willDoNothing().given(postService).updatePost(anyLong(), anyLong(), any(PostRequest.class));

        this.mockMvc.perform(put(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/{boardNo}", 1L)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).updatePost(anyLong(), anyLong(), any(PostRequest.class));
    }

    @Test
    @DisplayName("1:1 문의 상태 변경 - 관리자")
    void testUpdatePostStatus() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new PostStatusUpdateRequest());

        willDoNothing().given(postService).updateInquiryStatus(anyLong(), any(PostStatusUpdateRequest.class));

        this.mockMvc.perform(patch(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries/{boardNo}", 1L)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                    .andExpect(status().isOk());

        then(postService).should().updateInquiryStatus(anyLong(), any(PostStatusUpdateRequest.class));
    }

    @Test
    @DisplayName("1:1 문의 삭제 - 관리자")
    void testDeleteOtoInquiries() throws Exception {
        willDoNothing().given(postService).deletePost(anyLong());

        this.mockMvc.perform(delete(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/{boardNo}", 1L)
                .headers(headers))
                    .andExpect(status().isNoContent());

        then(postService).should().deletePost(anyLong());
    }

}
