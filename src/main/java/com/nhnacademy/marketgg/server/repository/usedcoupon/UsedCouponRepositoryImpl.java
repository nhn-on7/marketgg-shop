// package com.nhnacademy.marketgg.server.repository.usedcoupon;
//
// import com.nhnacademy.marketgg.server.entity.QUsedCoupon;
// import com.nhnacademy.marketgg.server.entity.UsedCoupon;
// import com.querydsl.core.types.Projections;
// import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//
// import java.util.List;
//
// public class UsedCouponRepositoryImpl extends QuerydslRepositorySupport implements UsedCouponRepositoryCustom {
//
//     public UsedCouponRepositoryImpl() {
//         super(UsedCoupon.class);
//     }
//
//     // @Override
//     // public List<UsedCoupon> findByGivenCoupons(Long couponNo, Long memberNo) {
//     //     QUsedCoupon usedCoupon = QUsedCoupon.usedCoupon;
//     //
//     //     return from(usedCoupon)
//     //             .select(Projections.constructor(UsedCoupon.class,
//     //                     usedCoupon.pk))
//     //             .where(usedCoupon.pk.couponNo.eq(couponNo)
//     //                                          .and(usedCoupon.pk.memberNo.eq(memberNo)))
//     //             .fetch();
//     // }
// }
