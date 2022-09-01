package com.nhnacademy.marketgg.server.repository.pointhistory;

import com.nhnacademy.marketgg.server.dto.response.point.PointRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.PointHistory;
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

        QueryResults<PointRetrieveResponse> queryResults = from(pointHistory)
            .select(Projections.constructor(PointRetrieveResponse.class,
                                            pointHistory.member.id,
                                            pointHistory.point,
                                            pointHistory.totalPoint,
                                            pointHistory.content,
                                            pointHistory.updatedAt))
            .where(pointHistory.member.id.eq(id))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    @Override
    public Page<PointRetrieveResponse> findAllForAdmin(final Pageable pageable) {
        QPointHistory pointHistory = QPointHistory.pointHistory;

        QueryResults<PointRetrieveResponse> queryResults = from(pointHistory)
            .select(Projections.constructor(PointRetrieveResponse.class,
                                            pointHistory.member.id,
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
