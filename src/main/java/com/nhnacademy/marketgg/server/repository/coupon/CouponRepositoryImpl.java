package com.nhnacademy.marketgg.server.repository.coupon;

import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.QCoupon;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CouponRepositoryImpl extends QuerydslRepositorySupport implements CouponRepositoryCustom {

    public CouponRepositoryImpl() {
        super(Coupon.class);
    }

    @Override
    public Coupon findByCouponId(Long couponId) {
        QCoupon coupon = QCoupon.coupon;

        return from(coupon)
                .select(selectAllCouponColumns())
                .fetchOne();
    }

    @Override
    public List<Coupon> findAllCoupons() {
        QCoupon coupon = QCoupon.coupon;

        return from(coupon)
                .select(selectAllCouponColumns())
                .fetch();
    }

    private ConstructorExpression<Coupon> selectAllCouponColumns() {
        QCoupon coupon = QCoupon.coupon;

        return Projections.constructor(Coupon.class,
                coupon.id,
                coupon.name,
                coupon.type,
                coupon.expiredDate,
                coupon.minimumMoney,
                coupon.discountAmount);
    }

}
