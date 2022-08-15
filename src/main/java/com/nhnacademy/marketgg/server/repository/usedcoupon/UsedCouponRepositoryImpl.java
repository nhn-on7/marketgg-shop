package com.nhnacademy.marketgg.server.repository.usedcoupon;

import com.nhnacademy.marketgg.server.entity.QUsedCoupon;
import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class UsedCouponRepositoryImpl extends QuerydslRepositorySupport implements UsedCouponRepositoryCustom {

    public UsedCouponRepositoryImpl() {
        super(UsedCoupon.class);
    }

    @Override
    public boolean existsCouponId(Long couponId) {
        QUsedCoupon usedCoupon = QUsedCoupon.usedCoupon;

        return from(usedCoupon)
                .where(usedCoupon.pk.couponId.eq(couponId))
                .select(usedCoupon.pk.couponId)
                .fetchFirst() != null;
    }
}
