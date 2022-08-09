package com.nhnacademy.marketgg.server.service.coupon;

import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.coupon.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.coupon.GivenCouponResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * 지급 쿠폰 관리 서비스입니다.
 *
 * @since 1.0.0
 */
public interface GivenCouponService {

    /**
     * 입력받은 회원 id 와 쿠폰 번호로 지급 쿠폰을 생성합니다.
     *
     * @param memberInfo         - 등록할 회원의 정보입니다.
     * @param givenCouponRequest - 쿠폰 번호를 가진 요청 DTO 입니다.
     * @since 1.0.0
     */
    void createGivenCoupons(MemberInfo memberInfo, GivenCouponCreateRequest givenCouponRequest);

    /**
     * 회원에게 지급된 전체 지급 쿠폰 목록을 조회합니다.
     *
     * @param memberInfo - 조회할 회원의 정보입니다.
     * @return - 회원에게 지급된 전체 지급 쿠폰 목록을 List 로 반환합니다.
     * GivenCouponResponse - 지급 쿠폰 응답 DTO 에는 쿠폰 상태값, 만료일자가 추가되어 있습니다.
     * @since 1.0.0
     */
    List<GivenCouponResponse> retrieveGivenCoupons(MemberInfo memberInfo, Pageable pageable);

}
