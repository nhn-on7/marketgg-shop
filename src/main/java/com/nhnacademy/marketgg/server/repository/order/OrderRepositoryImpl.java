package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.dto.response.OrderResponse;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.QOrder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

public class OrderRepositoryImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    QOrder order = QOrder.order;

    @Override
    public List<OrderResponse> findOrderList(Long memberId, boolean isUser) {
        return from(order)
                .select(selectOrderResponse())
                .where(eqMemberId(memberId, isUser))
                .fetch();
    }

    private ConstructorExpression<OrderResponse> selectOrderResponse() {
        return Projections.constructor(OrderResponse.class,
                                order.id,
                                order.member.id,
                                order.totalAmount,
                                order.orderStatus,
                                order.createdAt
        );
    }

    private BooleanExpression eqMemberId(Long memberId, boolean isUser) {
        if (isUser) {
            return order.member.id.eq(memberId);
        }
        return null;
    }

}
