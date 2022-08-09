package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderRetrieveResponse;
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
    public List<OrderRetrieveResponse> findOrderList(final Long memberId, final boolean isUser) {
        return from(order).select(selectOrderResponse())
                          .where(eqMemberId(memberId, isUser))
                          .fetch();
    }

    @Override
    public OrderDetailRetrieveResponse findOrderDetail(final Long orderId, final Long memberId, final boolean isUser) {
        return from(order).select(selectOrderDetailResponse())
                          .where(order.id.eq(orderId))
                          .where(eqMemberId(memberId, isUser))
                          .fetchOne();
    }

    private ConstructorExpression<OrderRetrieveResponse> selectOrderResponse() {
        return Projections.constructor(OrderRetrieveResponse.class,
                                       order.id,
                                       order.member.id,
                                       order.totalAmount,
                                       order.orderStatus,
                                       order.createdAt);
    }

    private ConstructorExpression<OrderDetailRetrieveResponse> selectOrderDetailResponse() {
        return Projections.constructor(OrderDetailRetrieveResponse.class,
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
