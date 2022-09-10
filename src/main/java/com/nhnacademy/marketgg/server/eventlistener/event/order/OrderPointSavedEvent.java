package com.nhnacademy.marketgg.server.eventlistener.event.order;

import static com.nhnacademy.marketgg.server.constant.PointContent.ORDER;

import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 주문(결제)을 했을 때 포인트 이력을 기록하기 위한 이벤트 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public class OrderPointSavedEvent {

    private Order order;

    private PointHistoryRequest pointRequest;

}
