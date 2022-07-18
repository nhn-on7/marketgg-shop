package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CouponRequest;
import com.nhnacademy.marketgg.server.dto.response.CouponRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCouponService implements CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    @Override
    public void createCoupon(CouponRequest couponRequest) {
        Coupon coupon = couponRepository.findById(couponRequest.getId()).orElseThrow(CouponNotFoundException::new);

        couponRepository.save(coupon);
    }

    @Override
    public List<CouponRetrieveResponse> retrieveCoupons() {
        return couponRepository.findAllCoupons();
    }

    @Transactional
    @Override
    public void deleteCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CouponNotFoundException::new);

        couponRepository.delete(coupon);
    }

}
