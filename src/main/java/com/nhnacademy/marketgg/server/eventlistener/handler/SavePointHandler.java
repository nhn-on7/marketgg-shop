package com.nhnacademy.marketgg.server.eventlistener.handler;

import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import com.nhnacademy.marketgg.server.eventlistener.event.savepoint.SavePointEvent;
import com.nhnacademy.marketgg.server.repository.pointhistory.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * SavePointEvent 를 처리하는 Handler 입니다.
 *
 * @author 민아영
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class SavePointHandler {

    private final PointHistoryRepository pointRepository;

    /**
     * 실제로 적립될 포인를을 생성하고 저장하는 EventListener 입니다.
     * SavePointEvent 이 발행되면 Listener 가 실행됩니다.
     *
     * @param pointRequest 지급될 포인트의 정보를 담고 있는 이벤트 객체입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void savePointByEvent(SavePointEvent pointRequest) {
        Member member = pointRequest.getMember();
        Integer totalPoint = pointRepository.findLastTotalPoints(member.getId());

        PointHistoryRequest pointHistoryRequest = pointRequest.getPointHistory();

        PointHistory pointHistory =
            new PointHistory(member, null,
                totalPoint + pointHistoryRequest.getPoint(), pointHistoryRequest);

        pointRepository.save(pointHistory);
    }

}
