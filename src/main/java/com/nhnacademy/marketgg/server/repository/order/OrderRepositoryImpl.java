package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.dto.response.OrderResponse;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.QOrder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class OrderRepositoryImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    QOrder order = QOrder.order;

    @Override
    public List<OrderResponse> findAllOrder() {
        return from(order)
                .select(selectOrderResponse())
                .fetch();
    }

    @Override
    public List<OrderResponse> findOrderListById(Long memberId) {
        return from(order)
                .select(selectOrderResponse())
                .where(order.member.id.eq(memberId))
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

}
