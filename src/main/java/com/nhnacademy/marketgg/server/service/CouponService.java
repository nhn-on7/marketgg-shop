package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.CouponRequest;
import com.nhnacademy.marketgg.server.dto.response.CouponRetrieveResponse;

import java.util.List;

public interface CouponService {

    void createCoupon(CouponRequest couponRequest);

    List<CouponRetrieveResponse> retrieveCoupons();

    void updateCoupon(Long couponId, CouponRequest couponRequest);

    void deleteCoupon(Long couponId);
}
