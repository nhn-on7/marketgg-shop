package com.nhnacademy.marketgg.server.eventlistener.handler;

import static com.nhnacademy.marketgg.server.constant.PointContent.ORDER;
import static com.nhnacademy.marketgg.server.constant.PointContent.ORDER_CANCEL;

import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import com.nhnacademy.marketgg.server.eventlistener.event.order.OrderPointCanceledEvent;
import com.nhnacademy.marketgg.server.repository.pointhistory.PointHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * OrderPointCanceledEvent 를 받아 처리하는 클래스입니다.
 *
 * @author 김정민
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class OrderPointCanceledEventHandler {

    private final PointHistoryRepository pointRepository;

    /**
     * 취소할 주문에 대한 적립, 차감된 포인트 이력을 찾아서
     * 현재 회원의 누적포인트를 새로 계산하고 새로운 포인트 이력을 남깁니다.
     *
     * @param event - 이벤트를 처리할 때 필요한 정보를 담고 있습니다.
     * @since 1.0.0
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void restorePoint(OrderPointCanceledEvent event) {
        Member member = event.getOrder().getMember();
        List<PointHistory> pointHistoryList = pointRepository.findByOrderId(event.getOrder().getId());
        Integer oldTotalPoint = pointRepository.findLastTotalPoints(member.getId());

        for (PointHistory pointHistory : pointHistoryList) {
            Integer point = pointHistory.getPoint();
            Integer newTotalPoint = pointHistory.getContent().equals(ORDER.getContent())
                    ? oldTotalPoint - point
                    : oldTotalPoint + point;

            PointHistoryRequest pointRequest = new PointHistoryRequest(point, ORDER_CANCEL.getContent());

            PointHistory saveHistory = new PointHistory(member, event.getOrder(), newTotalPoint, pointRequest);
            pointRepository.save(saveHistory);
        }
    }

}
