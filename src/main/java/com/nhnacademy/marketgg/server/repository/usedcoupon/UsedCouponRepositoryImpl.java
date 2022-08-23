package com.nhnacademy.marketgg.server.repository.usedcoupon;

import com.nhnacademy.marketgg.server.entity.QUsedCoupon;
import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Objects;
import java.util.Optional;

public class UsedCouponRepositoryImpl extends QuerydslRepositorySupport implements UsedCouponRepositoryCustom {

    public UsedCouponRepositoryImpl() {
        super(UsedCoupon.class);
    }

    @Override
    public boolean existsCouponId(final Long couponId) {
        QUsedCoupon usedCoupon = QUsedCoupon.usedCoupon;

        return Objects.nonNull(from(usedCoupon)
                                       .where(usedCoupon.pk.couponId.eq(couponId))
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

}
