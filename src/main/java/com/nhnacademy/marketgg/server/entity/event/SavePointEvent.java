package com.nhnacademy.marketgg.server.entity.event;

import com.nhnacademy.marketgg.server.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SavePointEvent {

    private Member member;

    private Integer point;

    private String content;

    private static final int NORMAL_REVIEW_POINT = 200;
    private static final int IMAGE_REVIEW_POINT = 500;
    private static final int REFERRED_POINT = 5000;

    public static SavePointEvent dispensePointForNormalReview(Member member) {
        return new SavePointEvent(member, NORMAL_REVIEW_POINT, "일반 리뷰 적립");
    }

    public static SavePointEvent dispensePointForImageReview(Member member) {
        return new SavePointEvent(member, IMAGE_REVIEW_POINT, "포토 리뷰 적립");
    }

    public static SavePointEvent dispensePointForReferred(Member member) {
        return new SavePointEvent(member, REFERRED_POINT, "추천인 적립");
    }

}
