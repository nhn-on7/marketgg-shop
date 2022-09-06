package com.nhnacademy.marketgg.server.repository.givencoupon;

import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class GivenCouponRepositoryImpl extends QuerydslRepositorySupport implements GivenCouponRepositoryCustom {

    public GivenCouponRepositoryImpl() {
        super(GivenCoupon.class);
    }

}
