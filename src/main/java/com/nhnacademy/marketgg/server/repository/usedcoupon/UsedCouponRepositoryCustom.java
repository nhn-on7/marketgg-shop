package com.nhnacademy.marketgg.server.repository.usedcoupon;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UsedCouponRepositoryCustom {

    /**
     * 쿠폰 번호가 사용 쿠폰에 등록되었는가를 확인하는 메소드입니다.
     *
     * @param couponId - 확인할 쿠폰의 식별번호입니다.
     * @return 등록되어 있다면 true(사용불가), 등록되어 있지 않다면 false(사용가능)를 반환합니다.
     * @since 1.0.0
     * @author 김정민
     */
    boolean existsCouponId(final Long couponId);

}
