package com.nhnacademy.marketgg.server.repository.coupon;

import com.nhnacademy.marketgg.server.entity.Coupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 쿠폰 레포지토리 입니다.
 *
 * @version 1.0.0
 */
public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {

}
