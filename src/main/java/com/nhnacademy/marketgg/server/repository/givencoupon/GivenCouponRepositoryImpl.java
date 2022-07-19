package com.nhnacademy.marketgg.server.repository.givencoupon;

import com.nhnacademy.marketgg.server.dto.response.GivenCouponResponse;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.QGivenCoupon;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class GivenCouponRepositoryImpl extends QuerydslRepositorySupport implements GivenCouponRepositoryCustom {

    public GivenCouponRepositoryImpl() {
        super(GivenCoupon.class);
    }

    @Override
    public List<GivenCouponResponse> findAllByMemberNo(Long id) {
        QGivenCoupon givenCoupon = QGivenCoupon.givenCoupon;

        return from(givenCoupon)
                .select(Projections.constructor(GivenCouponResponse.class,
                        givenCoupon.member.id,
                        givenCoupon.coupon.name,
                        givenCoupon.coupon.type,
                        givenCoupon.coupon.expiredDate,
                        givenCoupon.coupon.minimumMoney,
                        givenCoupon.coupon.discountAmount,
                        givenCoupon.createdAt))
                .where(givenCoupon.pk.memberNo.eq(id))
                .fetch();
    }
}
