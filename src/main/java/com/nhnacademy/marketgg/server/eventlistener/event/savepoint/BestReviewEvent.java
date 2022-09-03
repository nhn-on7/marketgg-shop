package com.nhnacademy.marketgg.server.eventlistener.event.savepoint;

import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.entity.Member;

/**
 * 베스트후기를 작성하면 발행하는 이벤트 구현체입니다.
 *
 * @author 조현진
 */
public class BestReviewEvent extends SavePointEvent {
    private static final int BEST_REVIEW_POINT = 3000;

    public BestReviewEvent(Member member, String content) {
        super(member, content);
    }

    /**
     * {@inheritDoc}
     *
     * @return 적립할 포인트의 Dto 를 반환합니다.
     * @author 조현진
     * @since 1.0.0
     */
    @Override
    public PointHistoryRequest getPointHistory() {
        return new PointHistoryRequest(BEST_REVIEW_POINT, super.getContent());
    }
}
