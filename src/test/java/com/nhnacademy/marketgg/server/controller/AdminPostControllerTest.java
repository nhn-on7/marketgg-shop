package com.nhnacademy.marketgg.server.controller;

import static com.nhnacademy.marketgg.server.aop.AspectUtils.AUTH_ID;
import static com.nhnacademy.marketgg.server.aop.AspectUtils.WWW_AUTHENTICATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(AdminPostController.class)
class AdminPostControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerServicePostService postService;

    @MockBean
    MemberRepository memberRepository;

    HttpHeaders headers;

    String uuid;
    Long memberId = 1L;

    private static final String DEFAULT_ADMIN_CUSTOMER_SERVICE = "/admin/customer-services";

    @BeforeEach
    void setUp(WebApplicationContext wac) throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                                 .alwaysDo(print())
                                 .build();

        uuid = UUID.randomUUID().toString();
        MemberInfo memberInfo = Dummy.getDummyMemberInfo(memberId, new Cart());
        given(memberRepository.findMemberInfoByUuid(uuid)).willReturn(Optional.of(memberInfo));

        String roles = objectMapper.writeValueAsString(Collections.singletonList(Role.ROLE_ADMIN));
        headers = new HttpHeaders();
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATION, roles);
    }

    @Test
    @DisplayName("1:1 문의 단건 조회 - 관리자")
    void testRetrievePost() throws Exception {
        given(postService.retrieveOtoInquiryPost(anyLong())).willReturn(null);

        this.mockMvc.perform(get(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries/{inquiryId}", 1L)
                                     .headers(headers))
                    .andExpect(status().isOk());

        then(postService).should().retrieveOtoInquiryPost(anyLong());
    }

    @Test
    @DisplayName("1:1 문의 목록 조회 - 관리자")
    void testRetrievePostList() throws Exception {
        given(postService.retrievePostList(anyString(), anyInt())).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries")
                                     .headers(headers))
                    .andExpect(status().isOk());

        then(postService).should().retrievePostList(anyString(), anyInt());
    }

    @Test
    @DisplayName("1:1 문의 상태 변경 - 관리자")
    void testUpdatePostStatus() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new PostStatusUpdateRequest());

        willDoNothing().given(postService)
                       .updateInquiryStatus(anyLong(), any(PostStatusUpdateRequest.class));

        this.mockMvc.perform(
                    patch(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries/{inquiryId}", 1L)
                            .headers(headers)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk());

        then(postService).should()
                         .updateInquiryStatus(anyLong(), any(PostStatusUpdateRequest.class));
    }

    @Test
    @DisplayName("1:1 문의 삭제 - 관리자")
    void testDeleteOtoInquiries() throws Exception {
        willDoNothing().given(postService).deletePost(anyLong());

        this.mockMvc.perform(
                    delete(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries/{inquiryId}", 1L)
                            .headers(headers))
                    .andExpect(status().isOk());

        then(postService).should().deletePost(anyLong());
    }

}
