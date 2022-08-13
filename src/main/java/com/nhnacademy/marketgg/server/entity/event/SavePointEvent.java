package com.nhnacademy.marketgg.server.entity.event;

import com.nhnacademy.marketgg.server.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 포인트 적립에 필요한 Data 를 가진 이벤트 입니다.
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

    private static final int NORMAL_REVIEW_POINT = 200;
    private static final int IMAGE_REVIEW_POINT = 500;
    private static final int REFERRED_POINT = 5000;

    /**
     * 일반 리뷰를 작성하면 발행하는 이벤트 생성 메소드 입니다.
     *
     * @param member - 포인트가 적립될 회원입니다.
     * @return 포인트 적립에 필요한 Data 를 가진 이벤트 객체를 반환합니다.
     * @author 조현진
     * @since 1.0.0
     */
    public static SavePointEvent dispensePointForNormalReview(Member member) {
        return new SavePointEvent(member, NORMAL_REVIEW_POINT, "일반 리뷰 적립");
    }

    /**
     * 포토 리뷰를 작성하면 발행하는 이벤트 생성 메소드 입니다.
     *
     * @param member - 포인트가 적립될 회원입니다.
     * @return 포인트 적립에 필요한 Data 를 가진 이벤트 객체를 반환합니다.
     * @author 조현진
     * @since 1.0.0
     */
    public static SavePointEvent dispensePointForImageReview(Member member) {
        return new SavePointEvent(member, IMAGE_REVIEW_POINT, "포토 리뷰 적립");
    }

    /**
     * 회원가입 시 추천을 하면 발행하는 이벤트 생성 메소드 입니다.
     *
     * @param member - 포인트가 적립될 회원입니다.
     * @return 포인트 적립에 필요한 Data 를 가진 이벤트 객체를 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    public static SavePointEvent dispensePointForReferred(Member member) {
        return new SavePointEvent(member, REFERRED_POINT, "추천인 적립");
    }

}
