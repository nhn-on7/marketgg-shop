package com.nhnacademy.marketgg.server.repository.order;

import static com.nhnacademy.marketgg.server.constant.OrderStatus.PAY_COMPLETE;

import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.QOrder;
import com.querydsl.core.types.Projections;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class OrderRepositoryImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> findOrderByMonth(Member member) {
        QOrder order = QOrder.order;

        LocalDate previousMonth = LocalDate.now().minusMonths(1L);
        LocalDateTime firstDate = previousMonth.withDayOfMonth(1).atStartOfDay();
        LocalDateTime lastDate = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()).atStartOfDay();

        return from(order)
            .select(Projections.constructor(Order.class,
                order.id, order.member, order.totalAmount, order.orderStatus, order.usedPoint, order.trackingNo,
                order.createdAt, order.updatedAt, order.deletedAt))
            .where(order.member.id.eq(member.getId())
                                  .and(order.orderStatus.eq(PAY_COMPLETE.status()))
                                  .and(order.createdAt.between(firstDate, lastDate)))
            .fetch();

    }
}
