package com.nhnacademy.marketgg.server.repository.coupon;

import com.nhnacademy.marketgg.server.dto.request.coupon.CouponDto;
import com.nhnacademy.marketgg.server.entity.Coupon;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 쿠폰 레포지토리입니다.
 *
 * @author 민아영
 * @version 1.0.0
 */
@NoRepositoryBean
public interface CouponRepositoryCustom {

    /**
     * 전체 쿠폰 목록을 반환합니다.
     *
     * @return 전체 쿠폰 목록을 List 로 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    Page<CouponDto> findAllCoupons(Pageable pageable);

    /**
     * 쿠폰 Id 로 일치하는 쿠폰 Dto 를 반환합니다.
     *
     * @param id - 쿠폰 Id 입니다.
     * @return CouponDto 를 Optional 로 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    Optional<CouponDto> findCouponDtoById(Long id);

    /**
     * 쿠폰 name 으로 일치하는 쿠폰 을 반환합니다.
     *
     * @param name - 쿠폰 name 입니다.
     * @return Coupon 을 Optional 로 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    Optional<Coupon> findCouponByName(String name);

    /**
     * 활성화 쿠폰 목록을 반환합니다.
     *
     * @return 활성화 쿠폰 목록을 List 로 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    Page<CouponDto> findActivateCouponDto(Pageable pageable);
}
