package com.nhnacademy.marketgg.server.repository.pointhistory;

import com.nhnacademy.marketgg.server.entity.PointHistory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class PointHistoryRepositoryImpl extends QuerydslRepositorySupport implements PointHistoryRepositoryCustom {

    public PointHistoryRepositoryImpl() {
        super(PointHistory.class);
    }

}
