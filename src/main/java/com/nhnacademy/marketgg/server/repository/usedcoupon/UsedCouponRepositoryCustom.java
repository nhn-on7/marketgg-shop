package com.nhnacademy.marketgg.server.repository.usedcoupon;

import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UsedCouponRepositoryCustom {

    /**
     * 해당하는 지급 쿠폰의 사용 여부 확인을 위해 사용 쿠폰을 조회하는 메소드입니다.
     *
     * @param givenCoupon - 조회할 쿠폰의 지급 쿠폰 입니다.
     * @return - 지급 쿠폰을 가진 사용 쿠폰을 반환합니다.
     * @since 1.0.0
     */
    List<UsedCoupon> findAllByGivenCoupon(GivenCoupon givenCoupon);

}
