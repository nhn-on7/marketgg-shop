package com.nhnacademy.marketgg.server.repository.coupon;

import com.nhnacademy.marketgg.server.entity.Coupon;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CouponRepositoryImpl extends QuerydslRepositorySupport implements CouponRepositoryCustom {

    public CouponRepositoryImpl() {
        super(Coupon.class);
    }

}
