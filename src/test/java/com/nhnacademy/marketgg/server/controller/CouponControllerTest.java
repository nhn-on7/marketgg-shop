package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.CouponDto;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.service.CouponService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CouponController.class)
public class CouponControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CouponService couponService;

    private static final String DEFAULT_COUPON = "/shop/v1/admin/coupons";

    Pageable pageable = PageRequest.of(0, 20);
    Page<GivenCoupon> inquiryPosts = new PageImpl<>(List.of(), pageable, 0);

    @Test
    @DisplayName("쿠폰 등록")
    void testCreateCoupon() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new CouponDto(1L, "name", "type", 10, 1000, 0.5));

        willDoNothing().given(couponService).createCoupon(any(CouponDto.class));

        this.mockMvc.perform(post(DEFAULT_COUPON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isCreated());

        then(couponService).should().createCoupon(any(CouponDto.class));
    }

    @Test
    @DisplayName("쿠폰 단건 조회")
    void testRetrieveCoupon() throws Exception {
        given(couponService.retrieveCoupon(anyLong())).willReturn(null);

        this.mockMvc.perform(get(DEFAULT_COUPON + "/" + 1L))
                .andExpect(status().isOk());

        then(couponService).should().retrieveCoupon(anyLong());
    }

    @Test
    @DisplayName("쿠폰 목록 조회")
    void testRetrieveCoupons() throws Exception {
        given(couponService.retrieveCoupons(pageable)).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_COUPON))
                .andExpect(status().isOk());

        then(couponService).should().retrieveCoupons(pageable);
    }

    @Test
    @DisplayName("쿠폰 수정")
    void testUpdateCoupon() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new CouponDto(1L, "name", "type", 10, 1000, 0.5));

        willDoNothing().given(couponService).updateCoupon(anyLong(), any(CouponDto.class));

        this.mockMvc.perform(put(DEFAULT_COUPON + "/" + 1L)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(requestBody))
                .andExpect(status().isOk());

        then(couponService).should().updateCoupon(anyLong(), any(CouponDto.class));
    }
    
    @Test
    @DisplayName("쿠폰 삭제")
    void testDeleteCoupon() throws Exception {
        willDoNothing().given(couponService).deleteCoupon(anyLong());

        this.mockMvc.perform(delete(DEFAULT_COUPON + "/" + 1L))
                .andExpect(status().isOk());

        then(couponService).should().deleteCoupon(anyLong());
    }

}
