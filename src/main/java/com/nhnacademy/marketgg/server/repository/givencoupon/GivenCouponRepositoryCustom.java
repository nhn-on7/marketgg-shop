package com.nhnacademy.marketgg.server.repository.givencoupon;

import com.nhnacademy.marketgg.server.dto.response.order.OrderGivenCoupon;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * 지급 쿠폰 레포지토리입니다.
 *
 * @author 민아영
 * @version 1.0.0
 */
@NoRepositoryBean
public interface GivenCouponRepositoryCustom {

    List<OrderGivenCoupon> findOwnCouponsByMemberId(Long memberId);

}
