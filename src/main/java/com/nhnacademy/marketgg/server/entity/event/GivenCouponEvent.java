package com.nhnacademy.marketgg.server.entity.event;

import static com.nhnacademy.marketgg.server.constant.CouponsName.BESTREVIEW;
import static com.nhnacademy.marketgg.server.constant.CouponsName.SIGNUP;

import com.nhnacademy.marketgg.server.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 쿠폰 지급에 필요한 Data 를 가진 이벤트 입니다.
 *
 * @author 민아영
 * @version 1.0.0
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public class GivenCouponEvent {

    private String couponName;

    private Member member;

    /**
     * 회원가입 시 발행하는 이벤트 생성 메소드 입니다.
     *
     * @param member - 쿠폰이 지급될 회원입니다.
     * @return 쿠폰 지급에 필요한 Data 를 가진 이벤트 객체를 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    public static GivenCouponEvent signUpCoupon(Member member) {
        return new GivenCouponEvent(SIGNUP.couponName(), member);
    }

    /**
     * 베스트 리뷰 선정 시 발행하는 이벤트 생성 메소드 입니다.
     *
     * @param member - 쿠폰이 지급될 회원입니다.
     * @return 쿠폰 지급에 필요한 Data 를 가진 이벤트 객체를 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    public static GivenCouponEvent bestReviewCoupon(Member member) {
        return new GivenCouponEvent(BESTREVIEW.couponName(), member);
    }

}
