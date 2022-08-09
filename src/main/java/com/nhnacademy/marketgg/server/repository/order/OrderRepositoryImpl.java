package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderResponse;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.QOrder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class OrderRepositoryImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    QOrder order = QOrder.order;

    @Override
    public List<OrderResponse> findOrderList(final Long memberId, final boolean isUser) {
        return from(order).select(selectOrderResponse())
                          .where(eqMemberId(memberId, isUser))
                          .fetch();
    }

    @Override
    public OrderDetailResponse findOrderDetail(final Long orderId, final Long memberId, final boolean isUser) {
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

    private BooleanExpression eqMemberId(final Long memberId, final boolean isUser) {
        if (isUser) {
            return order.member.id.eq(memberId);
        }
        return null;
    }

}
