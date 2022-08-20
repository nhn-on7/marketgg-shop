package com.nhnacademy.marketgg.server.eventlistener.event.savepoint;

import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원가입 시 추천을 하면 발행하는 이벤트 구현체 입니다.
 *
 * @author 조현진
 * @author 민아영
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class RecommendEvent extends SavePointEvent {

    private static final int REFERRED_POINT = 5000;

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
        return new PointHistoryRequest(REFERRED_POINT, content);
    }

}
