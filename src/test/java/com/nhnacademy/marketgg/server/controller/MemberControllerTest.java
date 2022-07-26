package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.aop.RoleCheckAspect;
import com.nhnacademy.marketgg.server.dto.request.GivenCouponRequest;
import com.nhnacademy.marketgg.server.dto.response.MemberResponse;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.service.GivenCouponService;
import com.nhnacademy.marketgg.server.service.MemberService;
import com.nhnacademy.marketgg.server.service.PointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.nhnacademy.marketgg.server.annotation.Role.ROLE_USER;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@Import({
        RoleCheckAspect.class
})
public class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @MockBean
    PointService pointService;

    @MockBean
    GivenCouponService givenCouponService;

    Pageable pageable = PageRequest.of(0, 20);

    @Test
    @DisplayName("GG 패스 갱신일자 확인")
    void testCheckPassUpdatedAt() throws Exception {
        when(memberService.retrievePassUpdatedAt(anyLong())).thenReturn(LocalDateTime.now());

        this.mockMvc.perform(get("/shop/v1/members/{memberId}/ggpass", 1L))
                    .andExpect(status().isOk());

        verify(memberService, times(1)).retrievePassUpdatedAt(anyLong());
    }

    @Test
    @DisplayName("GG 패스 가입")
    void testJoinPass() throws Exception {
        doNothing().when(memberService).subscribePass(anyLong());

        this.mockMvc.perform(post("/shop/v1/members/{memberId}/ggpass/subscribe", 1L))
                    .andExpect(status().isOk());

        verify(memberService, times(1)).subscribePass(anyLong());
    }

    @Test
    @DisplayName("GG 패스 해지")
    void testWithdrawPass() throws Exception {
        doNothing().when(memberService).withdrawPass(anyLong());

        this.mockMvc.perform(post("/shop/v1/members/{memberId}/ggpass/withdraw", 1L))
                    .andExpect(status().isOk());

        verify(memberService, times(1)).withdrawPass(anyLong());
    }

    @Test
    @DisplayName("사용자 조회")
    void testRetrieveMember() throws Exception {
        String uuid = "UUID";
        String roles = objectMapper.writeValueAsString(Collections.singleton(ROLE_USER));

        LocalDateTime now = LocalDateTime.now();
        MemberResponse memberResponse = MemberResponse.builder()
                                                      .memberGrade(null)
                                                      .ggpassUpdatedAt(now)
                                                      .birthDay(now.toLocalDate())
                                                      .gender('M')
                                                      .build();

        given(memberService.retrieveMember(uuid)).willReturn(memberResponse);

        this.mockMvc.perform(get("/shop/v1/members")
                    .header("AUTH-ID", uuid)
                    .header("WWW-Authentication", roles)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", equalTo(true)))
                    .andDo(print());
    }

    @Test
    @DisplayName("사용자 조회 실패")
    void testRetrieveMemberFail() throws Exception {
        String uuid = "UUID";
        String roles = objectMapper.writeValueAsString(Collections.singleton(ROLE_USER));
        given(memberService.retrieveMember(uuid)).willThrow(MemberNotFoundException.class);

        this.mockMvc.perform(get("/shop/v1/members")
                    .header("AUTH-ID", uuid)
                    .header("WWW-Authentication", roles)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.success", equalTo(false)))
                    .andDo(print());
    }

    @DisplayName("회원에게 지급 쿠폰 생성")
    void testCreateGivenCoupons() throws Exception {
        doNothing().when(givenCouponService).createGivenCoupons(anyLong(), any(GivenCouponRequest.class));
        String content = objectMapper.writeValueAsString(new GivenCouponRequest());

        this.mockMvc.perform(post("/shop/v1/members/{memberId}/coupons", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
                    .andExpect(status().isCreated());

        verify(givenCouponService, times(1)).createGivenCoupons(anyLong(), any(GivenCouponRequest.class));
    }

    @Test
    @DisplayName("회원에게 지급된 쿠폰 전체 조회")
    void testRetrieveGivenCoupons() throws Exception {
        when(givenCouponService.retrieveGivenCoupons(anyLong(), any(Pageable.class))).thenReturn(List.of());

        this.mockMvc.perform(get("/shop/v1/members/{memberId}/coupons", 1L))
                    .andExpect(status().isOk());

        verify(givenCouponService, times(1)).retrieveGivenCoupons(anyLong(), any(Pageable.class));
    }

}
