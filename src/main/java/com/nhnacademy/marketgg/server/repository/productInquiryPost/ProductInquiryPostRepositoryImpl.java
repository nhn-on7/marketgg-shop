package com.nhnacademy.marketgg.server.repository.productInquiryPost;

import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import com.nhnacademy.marketgg.server.entity.QProductInquiryPost;
import com.nhnacademy.marketgg.server.repository.productInquiryPost.ProductInquiryPostRepositoryCustom;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ProductInquiryPostRepositoryImpl extends QuerydslRepositorySupport implements ProductInquiryPostRepositoryCustom {

    public ProductInquiryPostRepositoryImpl() {
        super(ProductInquiryPost.class);
    }

    @Override
    public List<ProductInquiryResponse> findALLByProductNo(final Long id) {
        QProductInquiryPost productInquiryPost = QProductInquiryPost.productInquiryPost;

        return from(productInquiryPost)
                .select(Projections.constructor(ProductInquiryResponse.class,
                        productInquiryPost.member,
                        productInquiryPost.title,
                        productInquiryPost.content,
                        productInquiryPost.isSecret,
                        productInquiryPost.adminReply,
                        productInquiryPost.createdAt))
                .where(productInquiryPost.pk.productNo.eq(id))
                .fetch();
    }

    @Override
    public List<ProductInquiryResponse> findAllByMemberNo(final Long id) {
        QProductInquiryPost productInquiryPost = QProductInquiryPost.productInquiryPost;

        return from(productInquiryPost)
                .select(Projections.constructor(ProductInquiryResponse.class,
                        productInquiryPost.member,
                        productInquiryPost.title,
                        productInquiryPost.content,
                        productInquiryPost.isSecret,
                        productInquiryPost.adminReply,
                        productInquiryPost.createdAt))
                .where(productInquiryPost.member.id.eq(id))
                .fetch();
    }

}
