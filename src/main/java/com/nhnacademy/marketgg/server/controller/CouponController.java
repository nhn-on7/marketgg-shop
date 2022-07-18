package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop/v1/admin/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;


}
