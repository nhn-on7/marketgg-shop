package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.QOrder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class OrderRepositoryImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    QOrder order = QOrder.order;

    @Override
    public Page<OrderRetrieveResponse> findOrderList(final Long memberId, final boolean isAdmin,
                                                     final Pageable pageable) {

        QueryResults<OrderRetrieveResponse> result = from(order).select(selectOrderResponse())
                                                                .where(eqMemberId(memberId, isAdmin))
                                                                .where(userNotSeeDeleted(isAdmin, order.deletedAt))
                                                                .where(order.orderStatus.contains("취소/환불").not())
                                                                .offset(pageable.getOffset())
                                                                .limit(pageable.getPageSize())
                                                                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public OrderDetailRetrieveResponse findOrderDetail(final Long orderId, final Long memberId, final boolean isAdmin) {
        return from(order).select(selectOrderDetailResponse())
                          .where(order.id.eq(orderId))
                          .where(eqMemberId(memberId, isAdmin))
                          .where(userNotSeeDeleted(isAdmin, order.deletedAt))
                          .where(order.orderStatus.contains("취소/환불").not())
                          .fetchOne();
    }

    private ConstructorExpression<OrderRetrieveResponse> selectOrderResponse() {
        return Projections.constructor(OrderRetrieveResponse.class,
                                       order.id,
                                       order.member.id,
                                       order.orderName,
                                       order.totalAmount,
                                       order.orderStatus,
                                       order.createdAt);
    }

    private ConstructorExpression<OrderDetailRetrieveResponse> selectOrderDetailResponse() {
        return Projections.constructor(OrderDetailRetrieveResponse.class,
                                       order.id,
                                       order.member.id,
                                       order.orderName,
                                       order.totalAmount,
                                       order.orderStatus,
                                       order.usedPoint,
                                       order.trackingNo,
                                       order.zipCode,
                                       order.address,
                                       order.detailAddress,
                                       order.createdAt);
    }

    private BooleanExpression eqMemberId(final Long memberId, final boolean isAdmin) {
        if (isAdmin) {
            return null;
        }
        return order.member.id.eq(memberId);
    }

    private BooleanExpression userNotSeeDeleted(final boolean isUser, final DateTimePath<LocalDateTime> deletedAt) {
        if (isUser) {
            return deletedAt.isNull();
        }
        return null;
    }

}
