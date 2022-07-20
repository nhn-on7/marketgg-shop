package com.nhnacademy.marketgg.server.repository.usedcoupon;

import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class UsedCouponRepositoryImpl extends QuerydslRepositorySupport implements UsedCouponRepositoryCustom {

    public UsedCouponRepositoryImpl() {
        super(UsedCoupon.class);
    }

}
