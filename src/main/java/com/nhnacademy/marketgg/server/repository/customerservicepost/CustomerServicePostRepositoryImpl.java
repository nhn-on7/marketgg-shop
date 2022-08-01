package com.nhnacademy.marketgg.server.repository.customerservicepost;

import com.nhnacademy.marketgg.server.dto.response.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.QCustomerServicePost;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

public class CustomerServicePostRepositoryImpl extends QuerydslRepositorySupport implements CustomerServicePostRepositoryCustom {

    QCustomerServicePost csPost = QCustomerServicePost.customerServicePost;

    public CustomerServicePostRepositoryImpl() {
        super(CustomerServicePost.class);
    }

    @Override
    public PostResponseForOtoInquiry findOtoInquiryById(Long inquiryId) {
        return from(csPost)
                .where(csPost.id.eq(inquiryId))
                .select(selectAllCsPostColumns())
                .fetchOne();
    }

    @Override
    public Page<PostResponse> findPostsByCategoryId(final Pageable pageable, final String categoryId) {
        QueryResults<PostResponse> result = from(csPost)
                .where(csPost.category.id.eq(categoryId))
                .select(Projections.fields(PostResponse.class,
                                           csPost.id.as("boardNo"),
                                           csPost.category.id.as("categoryCode"),
                                           csPost.title,
                                           csPost.reason,
                                           csPost.status,
                                           csPost.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<PostResponse> findPostByCategoryAndMember(final Pageable pageable, final String categoryId, final Long memberId) {
        QueryResults<PostResponse> result = from(csPost)
                .where(csPost.category.id.eq(categoryId))
                .where(csPost.member.id.eq(memberId))
                .select(Projections.fields(PostResponse.class,
                                           csPost.id.as("boardNo"),
                                           csPost.category.id.as("categoryCode"),
                                           csPost.title,
                                           csPost.reason,
                                           csPost.status,
                                           csPost.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private QBean<PostResponseForOtoInquiry> selectAllCsPostColumns() {
        return Projections.fields(PostResponseForOtoInquiry.class,
                                       csPost.id,
                                       csPost.title,
                                       csPost.content,
                                       csPost.reason,
                                       csPost.status,
                                       csPost.createdAt,
                                       csPost.updatedAt
        );
    }
}
