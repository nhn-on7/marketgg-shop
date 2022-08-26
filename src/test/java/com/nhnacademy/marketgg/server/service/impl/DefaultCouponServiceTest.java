package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.request.coupon.CouponDto;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.service.coupon.DefaultCouponService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultCouponServiceTest {

    @InjectMocks
    DefaultCouponService couponService;

    @Mock
    CouponRepository couponRepository;

    private static CouponDto couponDto;
    private static Coupon coupon;

    Pageable pageable = PageRequest.of(0, 20);

    @BeforeAll
    static void beforeAll() {
        couponDto
            = new CouponDto(1L, "신규쿠폰", "정률할인", 1, 1, 0.5);
        coupon
            = new Coupon(1L, "신규쿠폰", "정률할인", 1, 1, 0.5, LocalDateTime.now());
    }

    @Test
    @DisplayName("쿠폰 등록 성공")
    void testCreateCouponSuccess() {
        couponService.createCoupon(couponDto);

        then(couponRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("쿠폰 목록 조회")
    void testRetrieveCoupons() {
        Page<CouponDto> pages = new PageImpl<>(List.of(), pageable, 1L);

        given(couponRepository.findAllCoupons(pageable))
            .willReturn(pages);

        PageEntity<CouponDto> couponResponses = couponService.retrieveCoupons(pageable);

        then(couponRepository).should(times(1)).findAllCoupons(pageable);
        assertThat(couponResponses).isNotNull();
    }

    @Test
    @DisplayName("쿠폰 수정 성공")
    void testUpdateCouponSuccess() {
        given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));

        couponService.updateCoupon(1L, couponDto);

        then(couponRepository).should(times(1)).findById(anyLong());
        then(couponRepository).should(times(1)).save(any(Coupon.class));
    }

    @Test
    @DisplayName("쿠폰 수정 실패(쿠폰 존재 X)")
    void testUpdateCouponFailWhenCouponNotFound() {
        given(couponRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> couponService.updateCoupon(1L, couponDto))
            .isInstanceOf(CouponNotFoundException.class);
    }

    @Test
    @DisplayName("쿠폰 삭제 성공")
    void testDeleteCouponSuccess() {
        given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));

        couponService.deleteCoupon(1L);

        then(couponRepository).should(times(1)).findById(anyLong());
        then(couponRepository).should(times(1)).save(any(Coupon.class));
    }

    @Test
    @DisplayName("쿠폰 삭제 실패(쿠폰 존재 X)")
    void testDeleteCouponFailWhenCouponNotFound() {
        given(couponRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> couponService.deleteCoupon(1L))
            .isInstanceOf(CouponNotFoundException.class);
    }

}
