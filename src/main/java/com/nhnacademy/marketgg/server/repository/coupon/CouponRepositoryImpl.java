package com.nhnacademy.marketgg.server.repository.coupon;

import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.QCoupon;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CouponRepositoryImpl extends QuerydslRepositorySupport implements CouponRepositoryCustom {

    public CouponRepositoryImpl() {
        super(Coupon.class);
    }

    QCoupon coupon = QCoupon.coupon;

    @Override
    public Page<Coupon> findAllCoupons(Pageable pageable) {

        List<Coupon> result = from(coupon)
                .select(selectAllCouponColumns())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    private ConstructorExpression<Coupon> selectAllCouponColumns() {

        return Projections.constructor(Coupon.class,
                                       coupon.id,
                                       coupon.name,
                                       coupon.type,
                                       coupon.expiredDate,
                                       coupon.minimumMoney,
                                       coupon.discountAmount);
    }

}
