package com.nhnacademy.marketgg.server.eventlistener.handler;

import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.eventlistener.event.savepoint.SavePointEvent;
import com.nhnacademy.marketgg.server.service.point.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * SavePointEvent 를 처리하는 Handler 입니다.
 *
 * @author 민아영
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SavePointHandler {

    private final PointService pointService;

    /**
     * 실제로 적립될 포인를을 생성하고 저장하는 EventListener 입니다.
     * SavePointEvent 이 발행되면 Listener 가 실행됩니다.
     *
     * @param pointRequest 지급될 포인트의 정보를 담고 있는 이벤트 객체입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Async
    @TransactionalEventListener
    public void savePointByEvent(final SavePointEvent pointRequest) {
        Member member = pointRequest.getMember();
        PointHistoryRequest pointHistoryRequest = pointRequest.getPointHistory();
        log.info("일반 리뷰 적립. 회원번호: {}, 내용: {}", pointRequest.getMember().getId(), pointRequest.getContent());
        pointService.createPointHistory(member.getId(), pointHistoryRequest);
    }

}

