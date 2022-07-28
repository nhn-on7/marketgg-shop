package com.nhnacademy.marketgg.server.repository.productinquirypost;

import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import com.nhnacademy.marketgg.server.entity.QProductInquiryPost;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ProductInquiryPostRepositoryImpl extends QuerydslRepositorySupport
        implements ProductInquiryPostRepositoryCustom {

    public ProductInquiryPostRepositoryImpl() {
        super(ProductInquiryPost.class);
    }

    @Override
    public Page<ProductInquiryResponse> findALLByProductNo(final Long id, final Pageable pageable) {
        QProductInquiryPost productInquiryPost = QProductInquiryPost.productInquiryPost;

        List<ProductInquiryResponse> result = from(productInquiryPost)
                .select(Projections.constructor(ProductInquiryResponse.class,
                        productInquiryPost.member,
                        productInquiryPost.title,
                        productInquiryPost.content,
                        productInquiryPost.isSecret,
                        productInquiryPost.adminReply,
                        productInquiryPost.createdAt))
                .where(productInquiryPost.pk.productNo.eq(id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public Page<ProductInquiryResponse> findAllByMemberNo(final String uuid, final Pageable pageable) {
        QProductInquiryPost productInquiryPost = QProductInquiryPost.productInquiryPost;

        List<ProductInquiryResponse> result = from(productInquiryPost)
                .select(Projections.constructor(ProductInquiryResponse.class,
                        productInquiryPost.member,
                        productInquiryPost.title,
                        productInquiryPost.content,
                        productInquiryPost.isSecret,
                        productInquiryPost.adminReply,
                        productInquiryPost.createdAt))
                .where(productInquiryPost.member.uuid.eq(uuid))
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

}
