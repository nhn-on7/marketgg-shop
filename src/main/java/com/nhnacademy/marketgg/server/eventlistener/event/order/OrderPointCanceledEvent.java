package com.nhnacademy.marketgg.server.eventlistener.event.order;

import com.nhnacademy.marketgg.server.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 주문(결제)을 취소했을 때 포인트 이력을 삭제하기 위한 이벤트 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public class OrderPointCanceledEvent {

    private Order order;

}
