package com.nhnacademy.marketgg.server.controller;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.CouponDto;
import com.nhnacademy.marketgg.server.service.CouponService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CouponController.class)
public class CouponControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CouponService couponService;

    private static final String DEFAULT_COUPON = "/admin/coupons";

    Pageable pageable = PageRequest.of(0, 20);
    CouponDto couponDto;

    @BeforeEach
    void setUp() {
        couponDto = new CouponDto();
        ReflectionTestUtils.setField(couponDto, "id", 1L);
        ReflectionTestUtils.setField(couponDto, "name", "신규 쿠폰");
        ReflectionTestUtils.setField(couponDto, "type", "정률할인");
        ReflectionTestUtils.setField(couponDto, "expiredDate", 1);
        ReflectionTestUtils.setField(couponDto, "minimumMoney", 1);
        ReflectionTestUtils.setField(couponDto, "discountAmount", 0.5);
    }

    @Test
    @DisplayName("쿠폰 등록")
    void testCreateCoupon() throws Exception {
        String requestBody = objectMapper.writeValueAsString(couponDto);

        willDoNothing().given(couponService).createCoupon(couponDto);

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
        String requestBody = objectMapper.writeValueAsString(couponDto);

        willDoNothing().given(couponService).updateCoupon(1L, couponDto);

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
                    .andExpect(status().isNoContent());

        then(couponService).should().deleteCoupon(anyLong());
    }

}
