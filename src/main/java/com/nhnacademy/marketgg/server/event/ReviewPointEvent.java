package com.nhnacademy.marketgg.server.event;

import com.nhnacademy.marketgg.server.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewPointEvent {

    private Long reviewId;

    private Member member;

    private Long point;

    public static ReviewPointEvent dispensePointForNormalReview(Long reviewId, Member member) {
        return new ReviewPointEvent(reviewId, member, 200L);
    }

    public static ReviewPointEvent dispensePointForImageReview(Long reviewId, Member member) {
        return new ReviewPointEvent(reviewId, member, 500L);
    }

}
