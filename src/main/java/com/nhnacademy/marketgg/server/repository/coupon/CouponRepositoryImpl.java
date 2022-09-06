package com.nhnacademy.marketgg.server.repository.coupon;

import com.nhnacademy.marketgg.server.dto.request.coupon.CouponDto;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.QCoupon;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import java.util.Optional;
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
    public Page<CouponDto> findAllCoupons(final Pageable pageable) {

        QueryResults<CouponDto> result = from(coupon)
            .select(selectAllCouponColumns())
            .orderBy(coupon.id.desc())
            .where(coupon.deletedAt.isNull())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Optional<CouponDto> findCouponDtoById(final Long id) {

        CouponDto result = from(coupon)
            .select(selectAllCouponColumns())
            .where(coupon.id.eq(id))
            .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Coupon> findCouponByName(final String name) {

        Coupon result = from(coupon)
            .where(coupon.name.eq(name))
            .orderBy(coupon.id.desc())
            .fetchFirst();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<CouponDto> findActivateCouponDto(Pageable pageable) {

        QueryResults<CouponDto> result = from(coupon)
            .select(selectAllCouponColumns())
            .orderBy(coupon.id.desc())
            .where(coupon.deletedAt.isNull().and(coupon.isActive.eq(true)))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private ConstructorExpression<CouponDto> selectAllCouponColumns() {

        return Projections.constructor(CouponDto.class,
                                       coupon.id,
                                       coupon.name,
                                       coupon.type,
                                       coupon.expiredDate,
                                       coupon.minimumMoney,
                                       coupon.discountAmount,
                                       coupon.isActive);
    }

}
