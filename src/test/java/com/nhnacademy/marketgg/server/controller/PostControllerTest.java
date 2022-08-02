package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.aop.AuthInjectAspect;
import com.nhnacademy.marketgg.server.aop.MemberInfoAspect;
import com.nhnacademy.marketgg.server.aop.RoleCheckAspect;
import com.nhnacademy.marketgg.server.aop.UuidAspect;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.nhnacademy.marketgg.server.aop.AspectUtils.AUTH_ID;
import static com.nhnacademy.marketgg.server.aop.AspectUtils.WWW_AUTHENTICATION;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@ActiveProfiles({ "testdb", "common" })
@Import({
        RoleCheckAspect.class,
        AuthInjectAspect.class,
        UuidAspect.class,
        MemberInfoAspect.class
})
class PostControllerTest {

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerServicePostService postService;

    @MockBean
    MemberRepository memberRepository;

    HttpHeaders headers;

    String uuid;
    Long memberId = 1L;

    private static final String DEFAULT_POST = "/customer-services";

    @BeforeEach
    void setUp(WebApplicationContext wac) throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                                 .alwaysDo(print())
                                 .build();

        uuid = UUID.randomUUID().toString();
        MemberInfo memberInfo = Dummy.getDummyMemberInfo(memberId, new Cart());
        given(memberRepository.findMemberInfoByUuid(uuid)).willReturn(Optional.of(memberInfo));

        String roles = objectMapper.writeValueAsString(Collections.singletonList(Role.ROLE_USER));
        headers = new HttpHeaders();
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATION, roles);
    }

    @Test
    @DisplayName("1:1 문의 등록 - 사용자")
    void testCreateOtoInquiry() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new PostRequest());

        willDoNothing().given(postService).createPost(anyLong(), any(PostRequest.class));

        this.mockMvc.perform(post(DEFAULT_POST)
                                     .headers(headers)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(requestBody))
                    .andExpect(status().isCreated());

        then(postService).should().createPost(anyLong(), any(PostRequest.class));
    }

    @Test
    @DisplayName("고객센터 게시글 단건 조회 - 사용자")
    void testRetrievePost() throws Exception {
        given(postService.retrievePost(anyLong())).willReturn(Dummy.getDummyPostResponseForDetail());

        this.mockMvc.perform(get(DEFAULT_POST + "/{boardNo}", 1L)
                                     .headers(headers))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).retrievePost(anyLong());
    }

    @Test
    @DisplayName("1:1 문의 단건 조회 - 사용자")
    void testRetrieveOtoInquiry() throws Exception {
        given(postService.retrieveOtoInquiryPost(anyLong())).willReturn(Dummy.getDummyPostResponseForOtoInquiry());

        this.mockMvc.perform(get(DEFAULT_POST + "/oto-inquiries/{boardNo}", 1L)
                                     .headers(headers))
                    .andExpect(status().isOk());

        then(postService).should().retrieveOtoInquiryPost(anyLong());
    }

    @Test
    @DisplayName("지정한 게시판 타입의 회원의 모든 고객센터 게시글 목록 조회 - 사용자")
    void testRetrieveOwnPostList() throws Exception {
        given(postService.retrieveOwnPostList(anyInt(), anyString(), anyLong()))
                .willReturn(List.of(Dummy.getDummyPostResponse()));

        this.mockMvc.perform(get(DEFAULT_POST + "/categories/{categoryCode}", "702")
                                     .headers(headers)
                                     .param("page", "1"))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).retrieveOwnPostList(anyInt(), anyString(), anyLong());
    }

    @Test
    @DisplayName("지정한 게시판 타입의 전체 목록 검색 결과 조회 - 사용자")
    void testSearchPostListForCategory() throws Exception {
        String requestBody = objectMapper.writeValueAsString(Dummy.getSearchRequest());

        given(postService.searchForCategory(anyString(), any(SearchRequest.class)))
                .willReturn(List.of(Dummy.getSearchBoardResponse()));

        this.mockMvc.perform(post(DEFAULT_POST + "/categories/{categoryCode}/search", "701")
                                     .headers(headers)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(requestBody))
                    .andExpect(status().isOk());

        then(postService).should(times(1)).searchForCategory(anyString(), any(SearchRequest.class));
    }

    @Test
    @DisplayName("지정한 게시판 타입의 Reason 옵션으로 검색한 결과 조회 - 사용자")
    void testSearchPostListForReason() throws Exception {
        String requestBody = objectMapper.writeValueAsString(Dummy.getSearchRequest());

        given(postService.searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString()))
                .willReturn(List.of(Dummy.getSearchBoardResponse()));

        this.mockMvc.perform(post(DEFAULT_POST + "/categories/{categoryCode}/search/reason", "701")
                                     .headers(headers)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(requestBody)
                                     .param("option", "취소/환불/교환"))
                    .andExpect(status().isOk());

        then(postService).should(times(1))
                         .searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString());
    }

    @Test
    @DisplayName("지정한 게시판 타입의 Status 옵션으로 검색한 결과 조회 - 사용자")
    void testPostListForStatus() throws Exception {
        String requestBody = objectMapper.writeValueAsString(Dummy.getSearchRequest());

        given(postService.searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString()))
                .willReturn(List.of(Dummy.getSearchBoardResponse()));

        this.mockMvc.perform(post(DEFAULT_POST + "/categories/{categoryCode}/search/status", "701", "미답변")
                                     .headers(headers)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(requestBody)
                                     .param("option", "종료"))
                    .andExpect(status().isOk());

        then(postService).should(times(1))
                         .searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString());
    }

    @Test
    @DisplayName("1:1 문의 삭제 - 사용자")
    void testDeleteOtoInquiry() throws Exception {
        willDoNothing().given(postService).deletePost(anyLong());

        this.mockMvc.perform(delete(DEFAULT_POST + "/oto-inquiries/{boardNo}", 1L)
                                     .headers(headers))
                    .andExpect(status().isNoContent());

        then(postService).should(times(1)).deletePost(anyLong());
    }

    @Test
    @DisplayName("고객센터 게시글의 선택 가능한 사유 목록 조회 - 사용자")
    void testRetrieveAllReasonValues() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_POST + "/reasons")
                                                           .headers(headers))
                                          .andExpect(status().isOk())
                                          .andExpect(jsonPath("$.size()").value(9))
                                          .andReturn();
    }

}
