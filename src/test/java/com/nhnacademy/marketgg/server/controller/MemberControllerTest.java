package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.GivenCouponRequest;
import com.nhnacademy.marketgg.server.dto.response.GivenCouponResponse;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.service.GivenCouponService;
import com.nhnacademy.marketgg.server.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @MockBean
    GivenCouponService givenCouponService;

    Pageable pageable = PageRequest.of(0, 20);
    Page<GivenCoupon> inquiryPosts = new PageImpl<>(List.of(), pageable, 0);

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
