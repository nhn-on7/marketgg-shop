package com.nhnacademy.marketgg.server.repository.givencoupon;

import com.nhnacademy.marketgg.server.dto.response.order.OrderGivenCoupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.QCoupon;
import com.nhnacademy.marketgg.server.entity.QGivenCoupon;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class GivenCouponRepositoryImpl extends QuerydslRepositorySupport implements GivenCouponRepositoryCustom {

    public GivenCouponRepositoryImpl() {
        super(GivenCoupon.class);
    }

    @Override
    public List<OrderGivenCoupon> findOwnCouponsByMemberId(final Long memberId) {
        QGivenCoupon givenCoupon = QGivenCoupon.givenCoupon;
        QCoupon coupon = QCoupon.coupon;

        return from(givenCoupon)
                .innerJoin(givenCoupon.coupon, coupon)
                .where(givenCoupon.pk.memberNo.eq(memberId))
                .select(Projections.constructor(OrderGivenCoupon.class,
                                                coupon.id,
                                                coupon.name,
                                                givenCoupon.createdAt,
                                                coupon.expiredDate,
                                                coupon.minimumMoney,
                                                coupon.discountAmount))
                .fetch();
    }

}
