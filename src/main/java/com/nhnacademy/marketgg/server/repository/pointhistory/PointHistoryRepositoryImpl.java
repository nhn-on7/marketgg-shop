package com.nhnacademy.marketgg.server.repository.pointhistory;

import com.nhnacademy.marketgg.server.dto.response.point.PointRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import com.nhnacademy.marketgg.server.entity.QMember;
import com.nhnacademy.marketgg.server.entity.QOrder;
import com.nhnacademy.marketgg.server.entity.QPointHistory;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class PointHistoryRepositoryImpl extends QuerydslRepositorySupport implements PointHistoryRepositoryCustom {

    public PointHistoryRepositoryImpl() {
        super(PointHistory.class);
    }

    @Override
    public Page<PointRetrieveResponse> findAllByMemberId(final Long id, final Pageable pageable) {
        QPointHistory pointHistory = QPointHistory.pointHistory;
        QMember member = QMember.member;
        QOrder order = QOrder.order;

        QueryResults<PointRetrieveResponse> queryResults = from(pointHistory)
                .innerJoin(member)
                .where(pointHistory.member.id.eq(id))
                .innerJoin(order)
                .select(Projections.constructor(PointRetrieveResponse.class,
                                                member.id,
                                                order.id,
                                                pointHistory.point,
                                                pointHistory.totalPoint,
                                                pointHistory.content,
                                                pointHistory.updatedAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    @Override
    public Page<PointRetrieveResponse> findAllForAdmin(final Pageable pageable) {
        QPointHistory pointHistory = QPointHistory.pointHistory;
        QMember member = QMember.member;
        QOrder order = QOrder.order;

        QueryResults<PointRetrieveResponse> queryResults = from(pointHistory)
                .innerJoin(member).on(pointHistory.member.id.eq(member.id))
                .innerJoin(order).on(pointHistory.order.id.eq(order.id))
                .select(Projections.constructor(PointRetrieveResponse.class,
                                                member.id,
                                                order.id,
                                                pointHistory.point,
                                                pointHistory.totalPoint,
                                                pointHistory.content,
                                                pointHistory.updatedAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    @Override
    public Integer findLastTotalPoints(final Long memberId) {
        QPointHistory pointHistory = QPointHistory.pointHistory;

        return Optional.ofNullable(from(pointHistory)
                                           .where(pointHistory.member.id.eq(memberId))
                                           .orderBy(pointHistory.updatedAt.desc())
                                           .select(pointHistory.totalPoint)
                                           .fetchFirst()).orElse(0);
    }

    @Override
    public List<PointHistory> findByOrderId(final Long orderId) {
        QPointHistory pointHistory = QPointHistory.pointHistory;

        return from(pointHistory)
                .where(pointHistory.order.id.eq(orderId))
                .select(pointHistory)
                .fetch();
    }

}
