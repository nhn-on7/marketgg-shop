package com.nhnacademy.marketgg.server.event;

import com.nhnacademy.marketgg.server.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 적립될 포인트 정보를 가지고 있는 Event 입니다.
 *
 * @author 조현진
 * @author 민아영
 * @version 1.0.0
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public class SavePointEvent {

    private Member member;

    private Integer point;

    private String content;

    /**
     * 일반 리뷰 작성 시 적립될 포인트를 생성하는 정적 메소드 입니다.
     *
     * @param member - 포인트가 지급될 회원 입니다.
     * @return 적립할 포인트를 생성하기 위한 요청 Event 입니다.
     * @author 조현진
     * @since 1.0.0
     */
    public static SavePointEvent dispensePointForNormalReview(Member member) {
        return new SavePointEvent(member, 200, "일반 리뷰 적립");
    }

    /**
     * 포토 리뷰 작성 시 적립될 포인트를 생성하는 정적 메소드 입니다.
     *
     * @param member - 포인트가 지급될 회원 입니다.
     * @return 적립할 포인트를 생성하기 위한 요청 Event 입니다.
     * @author 조현진
     * @since 1.0.0
     */
    public static SavePointEvent dispensePointForImageReview(Member member) {
        return new SavePointEvent(member, 500, "포토 리뷰 적립");
    }

    /**
     * 회원가입 쿠폰을 생성하는 정적 메소드 입니다.
     *
     * @param member - 포인트가 지급될 회원 입니다.
     * @return 추천인 적립 포인트를 생성하기 위한 요청 Event 입니다.
     * @author 민아영
     * @since 1.0.0
     */
    public static SavePointEvent dispensePointForReferred(Member member) {
        return new SavePointEvent(member, 5000, "추천인 적립");
    }

}
