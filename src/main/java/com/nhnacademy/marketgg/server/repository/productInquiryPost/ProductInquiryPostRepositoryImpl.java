package com.nhnacademy.marketgg.server.repository.productInquiryPost;

import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import com.nhnacademy.marketgg.server.entity.QProductInquiryPost;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ProductInquiryPostRepositoryImpl extends QuerydslRepositorySupport implements ProductInquiryPostRepositoryCustom {

    public ProductInquiryPostRepositoryImpl() {
        super(ProductInquiryPost.class);
    }

    @Override
    public List<ProductInquiryResponse> findALLByProductNo(Long id) {
        QProductInquiryPost productInquiryPost = QProductInquiryPost.productInquiryPost;

        return from(productInquiryPost)
                .where(productInquiryPost.product.id.eq(id))
                .select(Projections.bean(ProductInquiryResponse.class,
                        productInquiryPost.title,
                        productInquiryPost.content,
                        productInquiryPost.isSecret,
                        productInquiryPost.adminReply))
                .fetch();
    }

    @Override
    public List<ProductInquiryResponse> findAllByMemberNo(Long id) {
        QProductInquiryPost productInquiryPost = QProductInquiryPost.productInquiryPost;

        return from(productInquiryPost)
                .where(productInquiryPost.member.id.eq(id))
                .select(Projections.bean(ProductInquiryResponse.class,
                        productInquiryPost.title,
                        productInquiryPost.content,
                        productInquiryPost.isSecret,
                        productInquiryPost.adminReply))
                .fetch();
    }

}
