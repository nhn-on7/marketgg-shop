package com.nhnacademy.marketgg.server.repository.givencoupon;

import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GivenCouponRepository extends JpaRepository<GivenCoupon, GivenCoupon.Pk>, GivenCouponRepositoryCustom {
    List<GivenCoupon> findAllByMember_Id(Long id);
}
