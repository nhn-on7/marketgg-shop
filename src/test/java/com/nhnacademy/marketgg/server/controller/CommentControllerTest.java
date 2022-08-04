package com.nhnacademy.marketgg.server.controller;

import static com.nhnacademy.marketgg.server.aop.AspectUtils.AUTH_ID;
import static com.nhnacademy.marketgg.server.aop.AspectUtils.WWW_AUTHENTICATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.nhnacademy.marketgg.server.dto.request.customerservice.CommentRequest;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.OtoInquiryCommentService;
import java.util.Collections;
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
class CommentControllerTest {

    MockMvc mockMvc;

    @MockBean
    OtoInquiryCommentService otoInquiryCommentService;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberRepository memberRepository;

    HttpHeaders headers;

    String uuid;
    Long memberId = 1L;

    private static final String DEFAULT_CS_COMMENT = "/customer-services/oto-inquiries";

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
        headers.set(WWW_AUTHENTICATE, roles);
    }

    @Test
    @DisplayName("고객센터 게시글에 댓글 등록")
    void testCreateComment() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new CommentRequest());

        willDoNothing().given(otoInquiryCommentService)
                       .createComment(anyLong(), anyLong(), any(CommentRequest.class));

        this.mockMvc.perform(post(DEFAULT_CS_COMMENT + "/{inquiryId}/comments", 1L)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                    .andExpect(status().isCreated());

        then(otoInquiryCommentService).should()
                                      .createComment(anyLong(), anyLong(),
                                          any(CommentRequest.class));
    }

}
