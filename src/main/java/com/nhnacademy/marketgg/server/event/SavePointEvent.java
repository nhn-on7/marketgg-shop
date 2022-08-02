package com.nhnacademy.marketgg.server.event;

import com.nhnacademy.marketgg.server.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SavePointEvent {

    private Member member;

    private Integer point;

    private String content;

    public static SavePointEvent dispensePointForNormalReview(Member member) {
        return new SavePointEvent(member, 200, "일반 리뷰 적립");
    }

    public static SavePointEvent dispensePointForImageReview(Member member) {
        return new SavePointEvent(member, 500, "포토 리뷰 적립");
    }

    public static SavePointEvent dispensePointForReferred(Member member) {
        return new SavePointEvent(member, 5000, "추천인 적립");
    }

}
