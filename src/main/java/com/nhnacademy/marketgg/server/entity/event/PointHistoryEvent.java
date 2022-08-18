package com.nhnacademy.marketgg.server.entity.event;

import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 주문을 하거나 취소할 때 포인트 이력을 기록하기 위한 이벤트 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public class PointHistoryEvent {

    private Order order;
    private Integer point;
    private String content;

    /**
     * 주문을 하면 발행하는 이벤트 생성 메소드입니다.
     *
     * @param order - 포인트를 적립 대상인 주문 입니다.
     * @param point - 적립될 포인트 금액입니다.
     * @return 포인트 이력 기록에 필요한 Data 를 가진 이벤트 객체를 반환합니다.
     */
    public static PointHistoryEvent recordPointFromOrder(final Order order, final Integer point) {
        return new PointHistoryEvent(order, point, "주문 적립");
    }

    /**
     * 주문을 취소하면 발행하는 이벤트 생성 메소드입니다.
     * @param order - 포인트 적립 취소 대상인 주문입니다.
     * @param point - 적립 취소될 포인트 금액입니다.
     * @return 포인트 이력 기록에 필요한 Data 를 가진 이벤트 객체를 반환합니다.
     */
    public static PointHistoryEvent recordPointFromOrderCancel(final Order order, final Integer point) {
        return new PointHistoryEvent(order, point, "주문 취소");
    }

}
