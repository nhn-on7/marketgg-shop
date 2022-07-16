package com.nhnacademy.marketgg.server.repository.usedcoupon;

import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsedCouponRepository extends JpaRepository<UsedCoupon, UsedCoupon.Pk>, UsedCouponRepositoryCustom {

}
