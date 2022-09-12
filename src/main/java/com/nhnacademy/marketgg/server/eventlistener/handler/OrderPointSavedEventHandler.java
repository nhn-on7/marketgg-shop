package com.nhnacademy.marketgg.server.eventlistener.handler;

import com.nhnacademy.marketgg.server.constant.PointContent;
import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.eventlistener.event.order.OrderPointSavedEvent;
import com.nhnacademy.marketgg.server.service.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderPointSavedEventHandler {

    private final PointService pointService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createUsedCoupon(OrderPointSavedEvent event) {
        Order order = event.getOrder();
        Long memberId = order.getMember().getId();
        PointHistoryRequest request = event.getPointRequest();

        pointService.createPointHistoryForOrder(memberId, order.getId(),
                                                new PointHistoryRequest(-request.getPoint(), request.getContent()));
    }

}
