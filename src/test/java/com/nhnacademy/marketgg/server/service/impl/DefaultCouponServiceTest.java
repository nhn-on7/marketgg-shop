package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import com.nhnacademy.marketgg.server.dto.request.CouponDto;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultCouponServiceTest {

    @InjectMocks
    DefaultCouponService couponService;

    @Mock
    CouponRepository couponRepository;

    private static CouponDto couponDto;

    Pageable pageable = PageRequest.of(0, 20);

    @BeforeAll
    static void beforeAll() {
        couponDto = new CouponDto();
        ReflectionTestUtils.setField(couponDto, "id", 1L);
        ReflectionTestUtils.setField(couponDto, "name", "신규 쿠폰");
        ReflectionTestUtils.setField(couponDto, "type", "정률할인");
        ReflectionTestUtils.setField(couponDto, "expiredDate", 1);
        ReflectionTestUtils.setField(couponDto, "minimumMoney", 1);
        ReflectionTestUtils.setField(couponDto, "discountAmount", 0.5);
    }

    @Test
    @DisplayName("쿠폰 등록 성공")
    void testCreateCouponSuccess() {
        couponService.createCoupon(couponDto);

        then(couponRepository).should().save(any());
    }

    @Test
    @DisplayName("쿠폰 목록 조회")
    void testRetrieveCoupons() {
        Page<CouponDto> pages = new PageImpl<>(List.of(), pageable, 1L);

        given(couponRepository.findAllCoupons(pageable))
            .willReturn(pages);

        List<CouponDto> couponResponses = couponService.retrieveCoupons(pageable);

        then(couponRepository).should().findAllCoupons(pageable);
        assertThat(couponResponses).isNotNull();
    }

    @Test
    @DisplayName("쿠폰 수정 성공")
    void testUpdateCouponSuccess() {
        given(couponRepository.findById(anyLong())).willReturn(Optional.of(new Coupon(couponDto)));

        couponService.updateCoupon(1L, couponDto);

        then(couponRepository).should().findById(anyLong());
        then(couponRepository).should().save(any(Coupon.class));
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
        given(couponRepository.findById(anyLong())).willReturn(Optional.of(new Coupon(couponDto)));

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
