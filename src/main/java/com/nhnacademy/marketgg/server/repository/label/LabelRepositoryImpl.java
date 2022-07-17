package com.nhnacademy.marketgg.server.repository.label;

import com.nhnacademy.marketgg.server.dto.response.LabelRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.entity.QLabel;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class LabelRepositoryImpl extends QuerydslRepositorySupport implements LabelRepositoryCustom {

    public LabelRepositoryImpl() {
        super(Label.class);
    }

    @Override
    public List<LabelRetrieveResponse> findAllLabels() {
        QLabel label = QLabel.label;

        return from(label)
                .select(Projections.constructor(LabelRetrieveResponse.class,
                                         label.id,
                                         label.name))
                .fetch();
    }

}
