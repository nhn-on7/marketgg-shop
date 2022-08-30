package com.nhnacademy.marketgg.server.repository.dib;

import com.nhnacademy.marketgg.server.dto.response.dib.DibRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Dib;
import com.nhnacademy.marketgg.server.entity.QDib;
import com.nhnacademy.marketgg.server.entity.QImage;
import com.nhnacademy.marketgg.server.entity.QMember;
import com.nhnacademy.marketgg.server.entity.QProduct;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class DibRepositoryImpl extends QuerydslRepositorySupport implements DibRepositoryCustom {

    public DibRepositoryImpl() {
        super(Dib.class);
    }

    @Override
    public List<DibRetrieveResponse> findAllDibs(final Long memberId) {
        QDib dib = QDib.dib;
        QImage image = QImage.image;

        return from(dib)
                .select(Projections.constructor(DibRetrieveResponse.class,
                                                dib.product.id,
                                                image.imageAddress,
                                                dib.product.name,
                                                dib.product.price))
                .innerJoin(image).on(dib.product.asset.id.eq(image.id))
                .where(dib.member.id.eq(memberId))
                .fetch();
    }

}
