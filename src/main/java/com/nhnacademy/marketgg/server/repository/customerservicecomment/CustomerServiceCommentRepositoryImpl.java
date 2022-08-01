package com.nhnacademy.marketgg.server.repository.customerservicecomment;

import com.nhnacademy.marketgg.server.dto.response.CommentResponse;
import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.QCustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.QCustomerServicePost;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CustomerServiceCommentRepositoryImpl extends QuerydslRepositorySupport implements CustomerServiceCommentRepositoryCustom {

    public CustomerServiceCommentRepositoryImpl() {
        super(CustomerServiceComment.class);
    }

    QCustomerServiceComment comment = QCustomerServiceComment.customerServiceComment;
    QCustomerServicePost post = QCustomerServicePost.customerServicePost;

    @Override
    public CommentResponse findCommentById(Long commentId) {
        return from(comment)
                .where(comment.id.eq(commentId))
                .select(selectAllCommentColumns())
                .fetchOne();
    }

    @Override
    public List<CommentResponse> findByInquiryId(final Long inquiryId) {
        return from(comment)
                .innerJoin(post).on(comment.customerServicePost.id.eq(post.id))
                .where(post.id.eq(inquiryId))
                .select(selectAllCommentColumns())
                .fetch();
    }

    private ConstructorExpression<CommentResponse> selectAllCommentColumns() {
        return Projections.constructor(CommentResponse.class,
                                       comment.id,
                                       comment.customerServicePost.id,
                                       comment.member.id,
                                       comment.content,
                                       comment.createdDate);
    }

}
