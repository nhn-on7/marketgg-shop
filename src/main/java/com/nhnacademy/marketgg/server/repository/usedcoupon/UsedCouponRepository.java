package com.nhnacademy.marketgg.server.repository.usedcoupon;

import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsedCouponRepository extends JpaRepository<UsedCoupon, UsedCoupon.Pk>, UsedCouponRepositoryCustom {

    List<UsedCoupon> findAllByGivenCoupon(GivenCoupon givenCoupon);

}
