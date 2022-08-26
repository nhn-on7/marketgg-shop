package com.nhnacademy.marketgg.server.eventlistener.event.order;

import com.nhnacademy.marketgg.server.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 주문(결제)을 취소했을 때 사용한 쿠폰을 재사용할 수 있도록 사용쿠폰에서 삭제하는 이벤트 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public class OrderCouponCanceledEvent {

    private Order order;

}
