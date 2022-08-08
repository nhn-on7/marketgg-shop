package com.nhnacademy.marketgg.server.event;

import static com.nhnacademy.marketgg.server.constant.CouponName.SIGNUP;

import com.nhnacademy.marketgg.server.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 자동 지급될 쿠폰의 정보를 가지고 있는 Event 입니다.
 *
 * @author 민아영
 * @version 1.0.0
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public class SignedUpCouponEvent {

    private String couponName;

    private Member member;

    /**
     * 회원가입 쿠폰을 생성하는 정적 메소드 입니다.
     *
     * @author 민아영
     * @param member - 쿠폰이 지급될 회원 입니다.
     * @return 지급 쿠폰을 생성하기 위한 요청 Event 입니다.
     * @since 1.0.0
     */
    public static SignedUpCouponEvent signUpCoupon(Member member) {
        return new SignedUpCouponEvent(SIGNUP.couponName(), member);
    }

}
