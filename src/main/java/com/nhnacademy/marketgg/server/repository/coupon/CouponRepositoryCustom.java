package com.nhnacademy.marketgg.server.repository.coupon;

import com.nhnacademy.marketgg.server.dto.request.CouponDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 쿠폰 레포지토리입니다.
 *
 * @version 1.0.0
 */
@NoRepositoryBean
public interface CouponRepositoryCustom {

    /**
     * 전체 쿠폰 목록을 반환합니다.
     *
     * @return 전체 쿠폰 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    Page<CouponDto> findAllCoupons(Pageable pageable);

    Optional<CouponDto> findCouponDtoById(Long id);

}
