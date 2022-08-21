package com.nhnacademy.marketgg.server.eventlistener.event.savepoint;

import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 포토 리뷰를 작성하면 발행하는 이벤트 구현체 입니다.
 *
 * @author 조현진
 * @author 민아영
 * @version 1.0.0
 * @since 1.0.0
 */
public class PhotoReviewEvent extends SavePointEvent {

    private static final int IMAGE_REVIEW_POINT = 500;

    public PhotoReviewEvent(Member member, String content) {
        super(member, content);
    }

    /**
     * {@inheritDoc}
     *
     * @return 적립할 포인트의 Dto 를 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Override
    public PointHistoryRequest getPointHistory() {
        return new PointHistoryRequest(IMAGE_REVIEW_POINT, super.getContent());
    }

}
