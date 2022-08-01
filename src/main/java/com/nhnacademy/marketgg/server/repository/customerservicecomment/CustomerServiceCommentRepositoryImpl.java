package com.nhnacademy.marketgg.server.repository.customerservicecomment;

import com.nhnacademy.marketgg.server.dto.response.CommentResponse;
import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.QCustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.QCustomerServicePost;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CustomerServiceCommentRepositoryImpl extends QuerydslRepositorySupport implements CustomerServiceCommentRepositoryCustom {

    public CustomerServiceCommentRepositoryImpl() {
        super(CustomerServiceComment.class);
    }

    QCustomerServiceComment comment = QCustomerServiceComment.customerServiceComment;
    QCustomerServicePost post = QCustomerServicePost.customerServicePost;

    @Override
    public List<CommentResponse> findByInquiryId(final Long inquiryId) {
        return from(comment)
                .innerJoin(post).on(comment.customerServicePost.id.eq(post.id))
                .where(post.id.eq(inquiryId))
                .select(Projections.fields(CommentResponse.class,
                                           comment.content,
                                           comment.member.id.as("email"), // FIXME: id 로 email 조회할 수 있는 기능 구현 되면 수정하기
                                           comment.createdAt))
                .fetch();
    }

    @Override
    public List<Long> findCommentIdsByInquiryId(Long inquiryId) {
        return from(comment)
                .innerJoin(post).on(comment.customerServicePost.id.eq(post.id))
                .where(post.id.eq(inquiryId))
                .select(comment.id)
                .fetch();
    }

}
