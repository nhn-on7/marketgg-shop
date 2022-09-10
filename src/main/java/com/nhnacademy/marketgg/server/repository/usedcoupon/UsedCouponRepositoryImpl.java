package com.nhnacademy.marketgg.server.repository.usedcoupon;

import com.nhnacademy.marketgg.server.dto.response.coupon.UsedCouponResponse;
import com.nhnacademy.marketgg.server.entity.QCoupon;
import com.nhnacademy.marketgg.server.entity.QUsedCoupon;
import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import com.querydsl.core.types.Projections;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class UsedCouponRepositoryImpl extends QuerydslRepositorySupport implements UsedCouponRepositoryCustom {

    public UsedCouponRepositoryImpl() {
        super(UsedCoupon.class);
    }

    @Override
    public boolean existsCouponId(final Long couponId, final Long memberId) {
        QUsedCoupon usedCoupon = QUsedCoupon.usedCoupon;

        return Objects.nonNull(from(usedCoupon)
                                       .where(usedCoupon.pk.couponId.eq(couponId))
                                       .where(usedCoupon.pk.memberId.eq(memberId))
                                       .select(usedCoupon.pk.couponId)
                                       .fetchFirst());
    }

    @Override
    public Optional<Long> findByOrderId(final Long orderId) {
        QUsedCoupon usedCoupon = QUsedCoupon.usedCoupon;

        return Optional.ofNullable(from(usedCoupon)
                                           .where(usedCoupon.pk.orderId.eq(orderId))
                                           .select(usedCoupon.pk.couponId)
                                           .fetchOne());
    }

    @Override
    public UsedCouponResponse findUsedCouponName(final Long orderId) {
        QUsedCoupon usedCoupon = QUsedCoupon.usedCoupon;
        QCoupon coupon = QCoupon.coupon;

        return from(coupon)
                .innerJoin(usedCoupon).on(coupon.id.eq(usedCoupon.pk.couponId))
                .where(usedCoupon.pk.orderId.eq(orderId))
                .select(Projections.constructor(UsedCouponResponse.class,
                                                coupon.name,
                                                coupon.discountAmount,
                                                coupon.type))
                .fetchOne();
    }
}
