package com.nhnacademy.marketgg.server.eventlistener.event.order;

import static com.nhnacademy.marketgg.server.constant.PointContent.ORDER;

import com.nhnacademy.marketgg.server.entity.Order;
import lombok.Getter;

/**
 * 주문(결제)을 했을 때 포인트 이력을 기록하기 위한 이벤트 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 */
@Getter
public class OrderPointSavedEvent {

    private Order order;
    private Integer point;
    private String content;

    private OrderPointSavedEvent(final Order order, final Integer point, final String content) {
        this.order = order;
        this.point = point;
        this.content = content;
    }

    public static OrderPointSavedEvent restorePointHistory(final Order order, final Integer point) {
        return new OrderPointSavedEvent(order, point, order.getId() + " " + ORDER.getContent());
    }

}
