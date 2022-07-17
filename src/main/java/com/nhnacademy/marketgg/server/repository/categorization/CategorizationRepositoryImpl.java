package com.nhnacademy.marketgg.server.repository.categorization;

import com.nhnacademy.marketgg.server.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.QCategorization;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CategorizationRepositoryImpl extends QuerydslRepositorySupport implements CategorizationRepositoryCustom {

    public CategorizationRepositoryImpl() {
        super(Categorization.class);
    }

    @Override
    public List<CategorizationRetrieveResponse> findAllCategorization() {
        QCategorization categorization = QCategorization.categorization;

        return from(categorization)
                .select(Projections.constructor(CategorizationRetrieveResponse.class,
                                         categorization.id,
                                         categorization.name))
                .fetch();
    }

}
