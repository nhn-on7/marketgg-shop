package com.nhnacademy.marketgg.server.entity.event;

import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import lombok.Getter;

import java.util.List;

import static com.nhnacademy.marketgg.server.constant.PointContent.ORDER_CANCEL;

/**
 * 주문(결제)을 취소했을 때 포인트 이력을 삭제하기 위한 이벤트 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 */
@Getter
public class OrderPointCanceledEvent {

    private Order order;

    private List<PointHistory> pointHistoryList;

    private String content;

    private OrderPointCanceledEvent(final Order order, final List<PointHistory> pointHistoryList,
                                    final String content) {
        this.order = order;
        this.pointHistoryList = pointHistoryList;
        this.content = content;
    }

    public static OrderPointCanceledEvent restorePointHistory(final Order order,
                                                              final List<PointHistory> pointHistoryList) {
        return new OrderPointCanceledEvent(order, pointHistoryList, order.getId() + " " + ORDER_CANCEL.getContent());
    }

}
