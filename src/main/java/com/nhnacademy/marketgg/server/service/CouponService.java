package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.CouponDto;
import com.nhnacademy.marketgg.server.entity.Coupon;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 쿠폰 서비스입니다.
 *
 * @since 1.0.0
 */
public interface CouponService {

    /**
     * 입력받은 정보로 쿠폰을 등록합니다.
     *
     * @param couponDto - 쿠폰 등록에 필요한 정보를 담은 DTO 입니다.
     * @since 1.0.0
     */
    void createCoupon(CouponDto couponDto);

    /**
     * couponId 에 해당하는 쿠폰을 조회합니다.
     *
     * @param couponId - 조회할 쿠폰의 식별번호 입니다.
     * @return 조회한 쿠폰의 정보를 담은 DTO 를 반환합니다.
     * @since 1.0.0
     */
    CouponDto retrieveCoupon(Long couponId);

    /**
     * 전체 쿠폰 목록을 조회합니다.
     *
     * @return - 전체 쿠폰 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<CouponDto> retrieveCoupons(Pageable pageable);

    /**
     * 입력받은 정보로 쿠폰을 수정합니다.
     *
     * @param couponId  - 수정할 쿠폰의 식별번호입니다.
     * @param couponDto - 쿠폰 수정에 필요한 정보를 담은 DTO 입니다.
     * @since 1.0.0
     */
    void updateCoupon(Long couponId, CouponDto couponDto);

    /**
     * 선택한 쿠폰을 삭제합니다.
     *
     * @param couponId - 삭제할 쿠폰의 식별번호입니다.
     * @since 1.0.0
     */
    void deleteCoupon(Long couponId);

}
