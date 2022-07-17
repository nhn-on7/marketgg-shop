package com.nhnacademy.marketgg.server.repository.dib;

import com.nhnacademy.marketgg.server.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Dib;
import com.nhnacademy.marketgg.server.entity.QDib;
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
    public List<DibRetrieveResponse> findAllDibs(Long memberId) {
        QDib dib = QDib.dib;
        QProduct product = QProduct.product;
        QMember member = QMember.member;

        return from(dib)
                .innerJoin(member).on(dib.pk.memberNo.eq(memberId))
                .innerJoin(product).on(dib.pk.productNo.eq(product.id))
                .where(dib.pk.memberNo.eq(memberId))
                .select(Projections.constructor(DibRetrieveResponse.class,
                                         dib.product.name,
                                         dib.product.price,
                                         dib.createdAt))
                .fetch();
    }

}
