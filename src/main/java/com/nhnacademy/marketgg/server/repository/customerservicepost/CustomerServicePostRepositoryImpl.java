package com.nhnacademy.marketgg.server.repository.customerservicepost;

import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForDetail;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.QCustomerServicePost;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CustomerServicePostRepositoryImpl
    extends QuerydslRepositorySupport implements CustomerServicePostRepositoryCustom {

    QCustomerServicePost csPost = QCustomerServicePost.customerServicePost;

    public CustomerServicePostRepositoryImpl() {
        super(CustomerServicePost.class);
    }

    @Override
    public PostResponseForOtoInquiry findOtoInquiryById(final Long inquiryId) {
        return from(csPost).where(csPost.id.eq(inquiryId))
                           .select(selectAllCsPostColumns())
                           .fetchOne();
    }

    @Override
    public PostResponseForOtoInquiry findOwnOtoInquiryById(Long boardNo, Long memberId) {
        return from(csPost).where(csPost.id.eq(boardNo))
                           .where(csPost.member.id.eq(memberId))
                           .select(selectAllCsPostColumns())
                           .fetchOne();
    }

    @Override
    public PostResponseForDetail findByBoardNo(final Long boardNo) {
        return from(csPost).where(csPost.id.eq(boardNo))
                           .select(Projections.fields(PostResponseForDetail.class,
                               csPost.id,
                               csPost.title,
                               csPost.content,
                               csPost.reason,
                               csPost.status,
                               csPost.createdAt,
                               csPost.updatedAt))
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
