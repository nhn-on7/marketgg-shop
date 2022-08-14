package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.QOrder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;

public class OrderRepositoryImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    QOrder order = QOrder.order;

    @Override
    public List<OrderRetrieveResponse> findOrderList(final Long memberId, final boolean isUser) {
        return from(order).select(selectOrderResponse())
                          .where(eqMemberId(memberId, isUser))
                          .where(userNotSeeDeleted(isUser, order.deletedAt))
                          .where(order.orderStatus.contains("취소/환불").not())
                          .fetch();
    }

    @Override
    public OrderDetailRetrieveResponse findOrderDetail(final Long orderId, final Long memberId, final boolean isUser) {
        return from(order).select(selectOrderDetailResponse())
                          .where(order.id.eq(orderId))
                          .where(eqMemberId(memberId, isUser))
                          .where(userNotSeeDeleted(isUser, order.deletedAt))
                          .where(order.orderStatus.contains("취소/환불").not())
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
                                       order.zipCode,
                                       order.address,
                                       order.detailAddress,
                                       order.createdAt);
    }

    private BooleanExpression eqMemberId(final Long memberId, final boolean isUser) {
        if (isUser) {
            return order.member.id.eq(memberId);
        }
        return null;
    }

    private BooleanExpression userNotSeeDeleted(final boolean isUser, final DateTimePath<LocalDateTime> deletedAt) {
        if (isUser) {
            return deletedAt.isNull();
        }
        return null;
    }

}
