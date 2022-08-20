package com.nhnacademy.marketgg.server.eventlistener.event.savepoint;

import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 일반 리뷰를 작성하면 발행하는 이벤트 구현체 입니다.
 *
 * @author 조현진, 민아영
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class NormalReviewedEvent extends SavePointEvent {

    private static final int NORMAL_REVIEW_POINT = 200;

    private final Member member;
    private final String content;

    /**
     * {@inheritDoc}
     *
     * @return 적립할 포인트의 Dto 를 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Override
    public PointHistoryRequest getPointHistory() {
        return new PointHistoryRequest(NORMAL_REVIEW_POINT, content);
    }
}


