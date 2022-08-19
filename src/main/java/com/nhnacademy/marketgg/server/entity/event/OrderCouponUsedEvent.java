package com.nhnacademy.marketgg.server.entity.event;

import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 주문(결제)을 했을 때 사용한 쿠폰을 재사용할 수 없도록 사용쿠폰에 등록하기 위한 이벤트 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public class OrderCouponUsedEvent {

    private Order order;

}
