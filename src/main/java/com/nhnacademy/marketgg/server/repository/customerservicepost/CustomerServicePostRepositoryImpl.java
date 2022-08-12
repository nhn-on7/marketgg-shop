package com.nhnacademy.marketgg.server.repository.customerservicepost;

import static com.querydsl.core.types.Projections.constructor;

import com.nhnacademy.marketgg.server.dto.response.customerservice.CommentReady;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseDto;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForReady;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.QCustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.QCustomerServicePost;
import com.querydsl.core.QueryResults;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * 고객센터 게시판의 Query DSL 사용처입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public class CustomerServicePostRepositoryImpl
        extends QuerydslRepositorySupport implements CustomerServicePostRepositoryCustom {

    QCustomerServicePost csPost = QCustomerServicePost.customerServicePost;
    QCustomerServiceComment csComment = QCustomerServiceComment.customerServiceComment;

    public CustomerServicePostRepositoryImpl() {
        super(CustomerServicePost.class);
    }

    @Override
    public PostResponseForReady findOwnOtoInquiry(final Long postNo, final Long memberId) {
        return new PostResponseForReady(from(csPost).where(csPost.id.eq(postNo).and(csPost.member.id.eq(memberId)))
                                                    .select(constructor(PostResponseDto.class,
                                                                        csPost.id,
                                                                        csPost.category.id.as("categoryCode"),
                                                                        csPost.title,
                                                                        csPost.content,
                                                                        csPost.reason,
                                                                        csPost.status,
                                                                        csPost.createdAt,
                                                                        csPost.updatedAt))
                                                    .fetchOne(), getCommentList(postNo));
    }

    @Override
    public PostResponseForReady findByBoardNo(final Long postNo) {
        return new PostResponseForReady(from(csPost).where(csPost.id.eq(postNo))
                                                    .select(constructor(PostResponseDto.class,
                                                                        csPost.id,
                                                                        csPost.category.id.as("categoryCode"),
                                                                        csPost.title,
                                                                        csPost.content,
                                                                        csPost.reason,
                                                                        csPost.status,
                                                                        csPost.createdAt,
                                                                        csPost.updatedAt))
                                                    .fetchOne(), getCommentList(postNo));
    }

    @Override
    public Page<PostResponse> findPostsByCategoryId(final Pageable pageable, final String categoryId) {
        QueryResults<PostResponse> result = from(csPost).where(csPost.category.id.eq(categoryId))
                                                        .select(constructor(PostResponse.class,
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

    @Override
    public Page<PostResponse> findPostByCategoryAndMember(final Pageable pageable, final String categoryId,
                                                          final Long memberId) {
        QueryResults<PostResponse> result = from(csPost).where(csPost.category.id.eq(categoryId))
                                                        .where(csPost.member.id.eq(memberId))
                                                        .select(constructor(PostResponse.class,
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

    private List<CommentReady> getCommentList(final Long postNo) {
        return from(csComment)
                .where(csComment.customerServicePost.id.eq(postNo))
                .select(constructor(CommentReady.class,
                                    csComment.content,
                                    csComment.member.uuid,
                                    csComment.createdAt))
                .fetch();
    }

}
