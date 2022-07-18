package com.nhnacademy.marketgg.server.repository.pointhistory;

import com.nhnacademy.marketgg.server.dto.response.PointRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import com.nhnacademy.marketgg.server.entity.QMember;
import com.nhnacademy.marketgg.server.entity.QOrder;
import com.nhnacademy.marketgg.server.entity.QPointHistory;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class PointHistoryRepositoryImpl extends QuerydslRepositorySupport implements PointHistoryRepositoryCustom {

    public PointHistoryRepositoryImpl() {
        super(PointHistory.class);
    }

    @Override
    public List<PointRetrieveResponse> findAllByMemberId(final Long id) {
        QPointHistory pointHistory = QPointHistory.pointHistory;
        QMember member = QMember.member;
        QOrder order = QOrder.order;

        return from(pointHistory)
                .innerJoin(member).on(pointHistory.member.id.eq(member.id))
                .where(pointHistory.member.id.eq(id))
                .innerJoin(order).on(pointHistory.order.id.eq(order.id))
                .select(Projections.constructor(PointRetrieveResponse.class,
                                                member.id,
                                                order.id,
                                                pointHistory.point,
                                                pointHistory.totalPoint,
                                                pointHistory.content,
                                                pointHistory.updatedAt))
                .fetch();
    }

    @Override
    public List<PointRetrieveResponse> findAllForAdmin() {
        QPointHistory pointHistory = QPointHistory.pointHistory;
        QMember member = QMember.member;
        QOrder order = QOrder.order;

        return from(pointHistory)
                .innerJoin(member).on(pointHistory.member.id.eq(member.id))
                .innerJoin(order).on(pointHistory.order.id.eq(order.id))
                .select(Projections.constructor(PointRetrieveResponse.class,
                                                member.id,
                                                order.id,
                                                pointHistory.point,
                                                pointHistory.totalPoint,
                                                pointHistory.content,
                                                pointHistory.updatedAt))
                .fetch();
    }

    @Override
    public Integer findLastTotalPoint(Long id) {
        QPointHistory pointHistory = QPointHistory.pointHistory;

        return from(pointHistory)
                .where(pointHistory.member.id.eq(id))
                .fetchOne().getTotalPoint();
    }

}
