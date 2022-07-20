package com.nhnacademy.marketgg.server.repository.usedcoupon;

import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UsedCouponRepositoryCustom {

    List<UsedCoupon> findByPkCouponNoAndPkMemberNo(final Long couponNo, final Long memberNo);

}
