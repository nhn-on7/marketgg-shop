package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CouponRequest;
import com.nhnacademy.marketgg.server.dto.response.CouponRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@Transactional
public class DefaultCouponServiceTest {

    @InjectMocks
    DefaultCouponService couponService;

    @Mock
    CouponRepository couponRepository;

    private static CouponRequest couponRequest;

    @BeforeAll
    static void beforeAll() {
        couponRequest = new CouponRequest();
    }

    @Test
    @DisplayName("쿠폰 등록 성공")
    void testCreateCouponSuccess() {
        couponService.createCoupon(couponRequest);

        then(couponRepository).should().save(any(Coupon.class));
    }

    @Test
    @DisplayName("쿠폰 목록 조회")
    void testRetrieveCoupons() {
        given(couponRepository.findAllCoupons())
                .willReturn(List.of(new CouponRetrieveResponse(1L, "name", "type", LocalDateTime.now(), 1000)));

        List<CouponRetrieveResponse> couponResponses = couponService.retrieveCoupons();

        assertThat(couponResponses).hasSize(1);
    }

    @Test
    @DisplayName("쿠폰 수정 성공")
    void testUpdateCouponSuccess() {
        given(couponRepository.findById(anyLong())).willReturn(Optional.of(new Coupon(couponRequest)));

        couponService.updateCoupon(1L, couponRequest);

        then(couponRepository).should().findById(anyLong());
        then(couponRepository).should().save(any(Coupon.class));
    }

    @Test
    @DisplayName("쿠폰 수정 실패(쿠폰 존재 X)")
    void testUpdateCouponFailWhenCouponNotFound() {
        given(couponRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> couponService.updateCoupon(1L, couponRequest))
                .isInstanceOf(CouponNotFoundException.class);
    }

    @Test
    @DisplayName("쿠폰 삭제 성공")
    void testDeleteCouponSuccess() {
        given(couponRepository.findById(anyLong())).willReturn(Optional.of(new Coupon(couponRequest)));

        couponService.deleteCoupon(1L);

        then(couponRepository).should().findById(anyLong());
        then(couponRepository).should().delete(any(Coupon.class));
    }

    @Test
    @DisplayName("쿠폰 삭제 실패(쿠폰 존재 X)")
    void testDeleteCouponFailWhenCouponNotFound() {
        given(couponRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> couponService.deleteCoupon(1L))
                .isInstanceOf(CouponNotFoundException.class);
    }

}
