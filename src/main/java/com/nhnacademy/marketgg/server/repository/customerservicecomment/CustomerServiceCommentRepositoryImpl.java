package com.nhnacademy.marketgg.server.repository.customerservicecomment;

import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.QCustomerServiceComment;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CustomerServiceCommentRepositoryImpl extends QuerydslRepositorySupport implements CustomerServiceCommentRepositoryCustom {

    public CustomerServiceCommentRepositoryImpl() {
        super(CustomerServiceComment.class);
    }

    @Override
    public List<CustomerServiceComment> findByInquiry(Long inquiryId) {
        QCustomerServiceComment customerServiceComment = QCustomerServiceComment.customerServiceComment;

        return from(customerServiceComment)
                .where(customerServiceComment.customerServicePost.id.eq(inquiryId))
                .select(Projections.constructor(CustomerServiceComment.class,
                                                customerServiceComment.id,
                                                customerServiceComment.customerServicePost,
                                                customerServiceComment.member,
                                                customerServiceComment.content,
                                                customerServiceComment.createdAt))
                .fetch();
    }

}
