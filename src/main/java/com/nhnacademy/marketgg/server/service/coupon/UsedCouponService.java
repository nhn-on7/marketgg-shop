package com.nhnacademy.marketgg.server.service.coupon;

import com.nhnacademy.marketgg.server.dto.request.coupon.UsedCouponDto;

/**
 * 사용 쿠폰 관리 서비스 입니다.
 *
 * @version 1.0.0
 */
public interface UsedCouponService {

    /**
     * 사용 쿠폰을 생성합니다.
     * 회원이 주문을 완료하면 주문정보, 회원번호, 쿠폰번호 을 받아서 사용 쿠폰을 생성합니다.
     *
     * @param usedCouponDto - 사용 쿠폰 생성을 위한 DTO 입니다.
     * @since 1.0.0
     */
    void createUsedCoupons(UsedCouponDto usedCouponDto);

    /**
     * 사용 쿠폰을 삭제합니다.
     * 회원이 주문을 취소하면 주문정보, 회원번호, 쿠폰번호 을 받아서 사용 쿠폰을 삭제합니다.
     *
     * @param usedCouponDto - 삭제할 사용 쿠폰의 DTO 입니다.
     * @since 1.0.0
     */
    void deleteUsedCoupons(UsedCouponDto usedCouponDto);

}
