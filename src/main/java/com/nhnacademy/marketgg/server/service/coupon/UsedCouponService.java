package com.nhnacademy.marketgg.server.service.coupon;

import com.nhnacademy.marketgg.server.dto.request.coupon.UsedCouponDto;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.UsedCoupon;

/**
 * 사용 쿠폰 관리 서비스 입니다.
 *
 * @author 민아영
 * @version 1.0.0
 */
public interface UsedCouponService {

    /**
     * 사용 쿠폰을 생성합니다.
     * 회원이 주문을 완료하면 주문정보, 회원번호, 쿠폰번호 을 받아서 사용 쿠폰을 생성합니다.
     *
     * @param usedCouponDto - 사용 쿠폰 생성을 위한 DTO 입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void createUsedCoupons(UsedCouponDto usedCouponDto);

    /**
     * 사용 쿠폰을 삭제합니다.
     * 회원이 주문을 취소하면 주문정보, 회원번호, 쿠폰번호 을 받아서 사용 쿠폰을 삭제합니다.
     *
     * @param usedCouponDto - 삭제할 사용 쿠폰의 DTO 입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void deleteUsedCoupons(UsedCouponDto usedCouponDto);

    /**
     *  UsedCouponDto 를 파라미터로 받아 UsedCoupon Entity 로 변환해주는 Default 메서드
     *
     * @param usedCouponDto - 사용한 쿠폰의 주문, 사용자 정보를 담고있는 Dto
     * @param order - 사용한 쿠폰의 주문 정보를 담고 있는 Entity 객체
     * @param givenCoupon - 사용 쿠폰의 지급 정보를 담고 있는 Entity 객체
     * @return
     */
    default UsedCoupon toEntity(final UsedCouponDto usedCouponDto, final Order order, final GivenCoupon givenCoupon) {
        return UsedCoupon.builder()
                         .pk(new UsedCoupon.Pk(order.getId(), usedCouponDto.getCouponId(), usedCouponDto.getMemberId()))
                         .order(order)
                         .givenCoupon(givenCoupon)
                         .build();
    }

}
