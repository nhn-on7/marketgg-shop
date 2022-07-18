package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.CouponRequest;
import com.nhnacademy.marketgg.server.dto.response.CouponRetrieveResponse;
import com.nhnacademy.marketgg.server.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/shop/v1/admin/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    private static final String DEFAULT_COUPON = "/shop/v1/admin/coupons";

    @PostMapping
    ResponseEntity<Void> createCoupon(@RequestBody final CouponRequest couponRequest) {
        couponService.createCoupon(couponRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_COUPON))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    @GetMapping
    ResponseEntity<List<CouponRetrieveResponse>> retrieveCoupons() {
        List<CouponRetrieveResponse> couponResponses = couponService.retrieveCoupons();

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_COUPON))
                             .body(couponResponses);
    }
}
