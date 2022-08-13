package com.nhnacademy.marketgg.server.repository.productinquirypost;

import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryByProductResponse;
import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryByMemberResponse;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import com.nhnacademy.marketgg.server.entity.QProductInquiryPost;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


public class ProductInquiryPostRepositoryImpl extends QuerydslRepositorySupport
    implements ProductInquiryPostRepositoryCustom {

    public ProductInquiryPostRepositoryImpl() {
        super(ProductInquiryPost.class);
    }

    @Override
    public Page<ProductInquiryByProductResponse> findAllByProductNo(final Long id, final Pageable pageable) {
        QProductInquiryPost productInquiryPost = QProductInquiryPost.productInquiryPost;

        List<ProductInquiryByProductResponse> result = from(productInquiryPost)
            .select(Projections.constructor(ProductInquiryByProductResponse.class,
                productInquiryPost.member.uuid,
                productInquiryPost.title,
                productInquiryPost.content,
                productInquiryPost.isSecret,
                productInquiryPost.adminReply,
                productInquiryPost.createdDate))
            .where(productInquiryPost.pk.productNo.eq(id))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public Page<ProductInquiryByMemberResponse> findAllByMemberNo(final Long id, final Pageable pageable) {
        QProductInquiryPost productInquiryPost = QProductInquiryPost.productInquiryPost;

        List<ProductInquiryByMemberResponse> result = from(productInquiryPost)
            .select(Projections.constructor(ProductInquiryByMemberResponse.class,
                productInquiryPost.member.uuid,
                productInquiryPost.pk.productNo,
                productInquiryPost.title,
                productInquiryPost.content,
                productInquiryPost.adminReply,
                productInquiryPost.createdDate))
            .where(productInquiryPost.member.id.eq(id))
            .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

}
