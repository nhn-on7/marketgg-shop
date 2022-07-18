package com.nhnacademy.marketgg.server.repository.coupon;

import com.nhnacademy.marketgg.server.dto.response.CouponRetrieveResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CouponRepositoryCustom {

    List<CouponRetrieveResponse> findAllCoupons();

}
