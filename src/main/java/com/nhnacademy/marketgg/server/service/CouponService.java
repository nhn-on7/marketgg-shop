package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.CouponRequest;
import com.nhnacademy.marketgg.server.dto.response.CouponRetrieveResponse;

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
     * @param couponRequest - 쿠폰 등록에 필요한 정보를 담은 DTO 입니다.
     *
     * @since 1.0.0
     */
    void createCoupon(CouponRequest couponRequest);

    /**
     * 전체 쿠폰 목록을 조회합니다.
     *
     * @return - 전체 카티고리 목록을 List 로 반환합니다.
     *
     * @since 1.0.0
     */
    List<CouponRetrieveResponse> retrieveCoupons();

    /**
     * 입력받은 정보로 쿠폰을 수정합니다.
     *
     * @param couponId - 수정할 쿠폰의 식별번호입니다.
     * @param couponRequest - 쿠폰 수정에 필요한 정보를 담은 DTO 입니다.
     *
     * @since 1.0.0
     */
    void updateCoupon(Long couponId, CouponRequest couponRequest);

    /**
     * 선택한 쿠폰을 삭제합니다.
     *
     * @param couponId - 삭제할 쿠폰의 식별번호입니다.
     *
     * @since 1.0.0
     */
    void deleteCoupon(Long couponId);
}
