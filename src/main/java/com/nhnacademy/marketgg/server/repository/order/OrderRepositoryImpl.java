package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.dto.response.OrderDetailResponse;
import com.nhnacademy.marketgg.server.dto.response.OrderResponse;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.QOrder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class OrderRepositoryImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    QOrder order = QOrder.order;

    @Override
    public List<OrderResponse> findOrderList(Long memberId, boolean isUser) {
        return from(order).select(selectOrderResponse())
                          .where(eqMemberId(memberId, isUser))
                          .fetch();
    }

    @Override
    public OrderDetailResponse findOrderDetail(Long orderId, Long memberId, boolean isUser) {
        return from(order).select(selectOrderDetailResponse())
                          .where(order.id.eq(orderId))
                          .where(eqMemberId(memberId, isUser))
                          .fetchOne();
    }

    private ConstructorExpression<OrderResponse> selectOrderResponse() {
        return Projections.constructor(OrderResponse.class,
                                       order.id,
                                       order.member.id,
                                       order.totalAmount,
                                       order.orderStatus,
                                       order.createdAt);
    }

    private ConstructorExpression<OrderDetailResponse> selectOrderDetailResponse() {
        return Projections.constructor(OrderDetailResponse.class,
                                       order.id,
                                       order.member.id,
                                       order.totalAmount,
                                       order.orderStatus,
                                       order.usedPoint,
                                       order.trackingNo,
                                       order.createdAt);
    }

    private BooleanExpression eqMemberId(Long memberId, boolean isUser) {
        if (isUser) {
            return order.member.id.eq(memberId);
        }
        return null;
    }

}
