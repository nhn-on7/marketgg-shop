package com.nhnacademy.marketgg.server.repository.usedcoupon;

import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 사용 쿠폰 레포지토리 입니다.
 *
 * @version 1.0.0
 */
public interface UsedCouponRepository extends JpaRepository<UsedCoupon, UsedCoupon.Pk>, UsedCouponRepositoryCustom {


}
