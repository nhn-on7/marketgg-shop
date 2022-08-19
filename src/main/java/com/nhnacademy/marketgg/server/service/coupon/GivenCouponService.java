package com.nhnacademy.marketgg.server.service.coupon;

import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.coupon.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.coupon.GivenCouponResponse;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;

/**
 * 지급 쿠폰 관리 서비스입니다.
 *
 * @author 민아영
 * @version 1.0.0
 * @since 1.0.0
 */
public interface GivenCouponService {

    /**
     * 입력받은 회원 id 와 쿠폰 번호로 지급 쿠폰을 생성합니다.
     *
     * @param memberInfo         - 등록할 회원의 정보입니다.
     * @param givenCouponRequest - 쿠폰 번호를 가진 요청 DTO 입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void createGivenCoupons(MemberInfo memberInfo, GivenCouponCreateRequest givenCouponRequest);

    /**
     * 회원에게 지급된 전체 지급 쿠폰 목록을 조회합니다.
     *
     * @param memberInfo - 조회할 회원의 정보입니다.
     * @return - 회원에게 지급된 전체 지급 쿠폰 목록을 List 로 반환합니다.
     * GivenCouponResponse - 지급 쿠폰 응답 DTO 에는 쿠폰 상태값, 만료일자가 추가되어 있습니다.
     * @author 민아영
     * @since 1.0.0
     */
    PageEntity<GivenCouponResponse> retrieveGivenCoupons(MemberInfo memberInfo, Pageable pageable);

    /**
     * 회원 정보와 쿠폰 정보를 가지고 GivenCoupon 을 생성합니다.
     *
     * @param coupon - 지급할 쿠폰의 정보입니다.
     * @param member - 쿠폰을 지급할 회원입니다.
     * @return 생성한 지급 쿠폰 Entity 를 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    default GivenCoupon toEntity(final Coupon coupon, final Member member) {
        return GivenCoupon.builder()
                          .pk(new GivenCoupon.Pk(coupon.getId(), member.getId()))
                          .coupon(coupon)
                          .member(member)
                          .createdAt(LocalDateTime.now())
                          .build();
    }

    /**
     * 회원에게 지급된 쿠폰 중에 (사용가능, 기간만료, 사용완료) 상태값을 포함하여 Dto 로 변환합니다.
     *
     * @param givenCoupon - 회원에게 지급된 지급 쿠폰 Entity 객체
     * @param status      - 지급 쿠폰의 상태
     * @param expiredDate - 쿠폰이 발급된 일자부터 유효한 유효회간
     * @author 민아영
     * @since 1.0.0
     * @return 변환한 지급 쿠폰 Dto 를 반환합니다.
     */
    default GivenCouponResponse toDto(final GivenCoupon givenCoupon,
                                      final String status,
                                      final LocalDateTime expiredDate) {
        return GivenCouponResponse.builder()
                                  .memberId(givenCoupon.getMember().getId())
                                  .couponId(givenCoupon.getCoupon().getId())
                                  .name(givenCoupon.getCoupon().getName())
                                  .type(givenCoupon.getCoupon().getType())
                                  .minimumMoney(givenCoupon.getCoupon().getMinimumMoney())
                                  .discountAmount(givenCoupon.getCoupon().getDiscountAmount())
                                  .expiredDate(expiredDate)
                                  .status(status)
                                  .build();
    }
}
