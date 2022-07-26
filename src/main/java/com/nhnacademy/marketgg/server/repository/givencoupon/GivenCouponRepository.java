package com.nhnacademy.marketgg.server.repository.givencoupon;

import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 지급 쿠폰 레포지토리입니다.
 *
 * @version 1.0.0
 */
public interface GivenCouponRepository extends JpaRepository<GivenCoupon, GivenCoupon.Pk>, GivenCouponRepositoryCustom {

    /**
     * 선택한 회원의 지급 쿠폰 목록을 반환하는 메소드입니다.
     *
     * @param id - 조회할 회원의 식별번호입니다.
     * @return - 조회된 지급 쿠폰 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    Page<GivenCoupon> findAllByMemberId(Long id, Pageable pageable);

}
