package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.UsedCouponDto;
import com.nhnacademy.marketgg.server.service.UsedCouponService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsedCouponController.class)
class UsedCouponControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UsedCouponService usedCouponService;

    String DEFAULT_USED_COUPONS = "/shop/v1/system/used-coupons";

    private static UsedCouponDto usedCouponDto;

    @BeforeAll
    static void beforeEach() {
        usedCouponDto = new UsedCouponDto();
    }

    @Test
    @DisplayName("사용 쿠폰 생성 테스트")
    void testCreateUsedCoupons() throws Exception {
        String requestBody = objectMapper.writeValueAsString(usedCouponDto);

        doNothing().when(usedCouponService).createUsedCoupons(any(UsedCouponDto.class));

        this.mockMvc.perform(post(DEFAULT_USED_COUPONS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("사용 쿠폰 삭제 테스트")
    void testDeleteUsedCoupons() throws Exception {
        String requestBody = objectMapper.writeValueAsString(usedCouponDto);

        doNothing().when(usedCouponService).deleteUsedCoupons(any(UsedCouponDto.class));

        this.mockMvc.perform(delete(DEFAULT_USED_COUPONS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isOk());
    }

}
