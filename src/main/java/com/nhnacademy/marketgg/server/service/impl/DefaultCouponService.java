package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCouponService implements CouponService {

    private final CouponRepository couponRepository;
}
