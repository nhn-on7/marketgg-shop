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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.nhnacademy.marketgg.server.aop.AspectUtils.AUTH_ID;
import static com.nhnacademy.marketgg.server.aop.AspectUtils.WWW_AUTHENTICATION;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
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
    @DisplayName("1:1 문의 등록")
    void testCreateOtoInquiry() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new PostRequest());

        willDoNothing().given(postService).createPost(anyLong(), any(PostRequest.class));

        this.mockMvc.perform(post(DEFAULT_POST + "/oto-inquiries")
                                     .headers(headers)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(requestBody))
                    .andExpect(status().isCreated());

        then(postService).should().createPost(anyLong(), any(PostRequest.class));
    }

    @Test
    @DisplayName("1:1 문의 단건 조회 - 사용자")
    void testRetrieveOtoInquiry() throws Exception {
        given(postService.retrieveOtoInquiryPost(anyLong())).willReturn(null);

        this.mockMvc.perform(get(DEFAULT_POST + "/oto-inquiries/{inquiryId}", 1L)
                                     .headers(headers))
                    .andExpect(status().isOk());

        then(postService).should().retrieveOtoInquiryPost(anyLong());
    }

    @Test
    @DisplayName("본인 1:1 문의 목록 조회 - 사용자")
    void testRetrieveOwnOtoInquiries() throws Exception {
        given(postService.retrievesOwnPostList(anyInt(), anyString(), anyLong())).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_POST + "/categories/702")
                                     .headers(headers)
                                     .param("page", "1"))
                    .andExpect(status().isOk());

        then(postService).should().retrievesOwnPostList(anyInt(), anyString(), anyLong());
    }

    @Test
    @DisplayName("1:1 문의 삭제 - 사용자")
    void testDeleteOtoInquiries() throws Exception {
        willDoNothing().given(postService).deletePost(anyLong());

        this.mockMvc.perform(delete(DEFAULT_POST + "/oto-inquiries/{inquiryId}", 1L)
                                     .headers(headers))
                    .andExpect(status().isOk());

        then(postService).should().deletePost(anyLong());
    }

    @Test
    @DisplayName("고객센터 게시글 사유 목록 조회")
    void testRetrieveAllReasonValues() throws Exception {
        this.mockMvc.perform(get(DEFAULT_POST + "/reasons")
                                     .headers(headers))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(9)));
    }

}
