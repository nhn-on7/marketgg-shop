package com.nhnacademy.marketgg.server.repository.coupon;

import com.nhnacademy.marketgg.server.dto.response.CouponRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.QCoupon;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CouponRepositoryImpl extends QuerydslRepositorySupport implements CouponRepositoryCustom {

    public CouponRepositoryImpl() {
        super(Coupon.class);
    }

    @Override
    public List<CouponRetrieveResponse> findAllCoupons() {
        QCoupon coupon = QCoupon.coupon;

        return from(coupon)
                .select(Projections.constructor(CouponRetrieveResponse.class,
                                                coupon.id,
                                                coupon.name,
                                                coupon.type,
                                                coupon.expiredDate,
                                                coupon.minimumMoney))
                .fetch();
    }

}
