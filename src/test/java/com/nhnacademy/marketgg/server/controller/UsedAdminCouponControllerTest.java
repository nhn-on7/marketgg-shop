package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.controller.coupon.UsedCouponController;
import com.nhnacademy.marketgg.server.dto.request.coupon.UsedCouponDto;
import com.nhnacademy.marketgg.server.service.coupon.UsedCouponService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UsedCouponController.class)
class UsedAdminCouponControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UsedCouponService usedCouponService;

    String DEFAULT_USED_COUPONS = "/system/used-coupons";

    private static UsedCouponDto usedCouponDto;

    @BeforeAll
    static void beforeEach() {
        usedCouponDto = new UsedCouponDto();
        ReflectionTestUtils.setField(usedCouponDto, "orderId", 1L);
        ReflectionTestUtils.setField(usedCouponDto, "couponId", 1L);
        ReflectionTestUtils.setField(usedCouponDto, "memberId", 1L);
    }

    @Test
    @DisplayName("사용 쿠폰 생성 테스트")
    void testCreateUsedCoupons() throws Exception {
        String requestBody = objectMapper.writeValueAsString(usedCouponDto);

        willDoNothing().given(usedCouponService).createUsedCoupons(any(UsedCouponDto.class));

        this.mockMvc.perform(post(DEFAULT_USED_COUPONS)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(requestBody))
                    .andExpect(status().isCreated());

        then(usedCouponService).should(times(1)).createUsedCoupons(any(UsedCouponDto.class));
    }

    @Test
    @DisplayName("사용 쿠폰 삭제 테스트")
    void testDeleteUsedCoupons() throws Exception {
        String requestBody = objectMapper.writeValueAsString(usedCouponDto);

        willDoNothing().given(usedCouponService).deleteUsedCoupons(any(UsedCouponDto.class));

        this.mockMvc.perform(delete(DEFAULT_USED_COUPONS)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(requestBody))
                    .andExpect(status().isNoContent());

        then(usedCouponService).should(times(1)).deleteUsedCoupons(any(UsedCouponDto.class));
    }

}
