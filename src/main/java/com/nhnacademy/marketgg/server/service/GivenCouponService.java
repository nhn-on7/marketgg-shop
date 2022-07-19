package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.GivenCouponRequest;
import com.nhnacademy.marketgg.server.dto.response.GivenCouponResponse;

import java.util.List;

public interface GivenCouponService {

    void createGivenCoupons(Long memberId, GivenCouponRequest givenCouponRequest);

    List<GivenCouponResponse> retrieveGivenCoupons(Long memberId);

}
