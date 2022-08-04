package com.nhnacademy.marketgg.server.repository.customerservicepost;

import com.nhnacademy.marketgg.server.dto.response.customerservice.CommentResponse;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForDetail;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.QCustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.QCustomerServicePost;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.JPAExpressions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CustomerServicePostRepositoryImpl
        extends QuerydslRepositorySupport implements CustomerServicePostRepositoryCustom {

    QCustomerServicePost csPost = QCustomerServicePost.customerServicePost;
    QCustomerServiceComment csComment = QCustomerServiceComment.customerServiceComment;

    public CustomerServicePostRepositoryImpl() {
        super(CustomerServicePost.class);
    }

    @Override
    public PostResponseForDetail findOwnOtoInquiry(final Long postNo, final Long memberId) {
        return from(csPost).where(csPost.id.eq(postNo).and(csPost.member.id.eq(memberId)))
                           .select(getDetailFields(postNo))
                           .innerJoin(csComment).on(csComment.customerServicePost.id.eq(csPost.id))
                           .fetchOne();
    }

    @Override
    public PostResponseForDetail findByBoardNo(final Long postNo) {
        return from(csPost).where(csPost.id.eq(postNo))
                           .select(getDetailFields(postNo))
                           .innerJoin(csComment).on(csComment.customerServicePost.id.eq(csPost.id))
                           .fetchOne();
    }

    @Override
    public Page<PostResponse> findPostsByCategoryId(final Pageable pageable, final String categoryId) {
        QueryResults<PostResponse> result = from(csPost).where(csPost.category.id.eq(categoryId))
                                                        .select(Projections.fields(PostResponse.class,
                                                                                   csPost.id,
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
    public Page<PostResponse> findPostByCategoryAndMember(final Pageable pageable, final String categoryId,
                                                          final Long memberId) {
        QueryResults<PostResponse> result = from(csPost).where(csPost.category.id.eq(categoryId))
                                                        .where(csPost.member.id.eq(memberId))
                                                        .select(Projections.fields(PostResponse.class,
                                                                                   csPost.id,
                                                                                   csPost.category.id.as(
                                                                                           "categoryCode"),
                                                                                   csPost.title,
                                                                                   csPost.reason,
                                                                                   csPost.status,
                                                                                   csPost.createdAt))
                                                        .offset(pageable.getOffset())
                                                        .limit(pageable.getPageSize())
                                                        .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private QBean<PostResponseForDetail> getDetailFields(final Long postNo) {
        return Projections.fields(PostResponseForDetail.class,
                                  csPost.id,
                                  csPost.category.id.as("categoryCode"),
                                  csPost.title,
                                  csPost.content,
                                  csPost.reason,
                                  csPost.status,
                                  csPost.createdAt,
                                  csPost.updatedAt,
                                  ExpressionUtils.as(JPAExpressions.select(Projections.constructor(
                                                                           CommentResponse.class,
                                                                           csComment.content,
                                                                           csComment.member.id,
                                                                           csComment.createdAt))
                                                                   .from(csComment)
                                                                   .where(csComment.customerServicePost.id.eq(
                                                                           postNo)), "commentList"));
    }

}
