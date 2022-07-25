package com.nhnacademy.marketgg.server.repository.customerservicepost;

import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.QCustomerServicePost;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CustomerServicePostRepositoryImpl extends QuerydslRepositorySupport implements CustomerServicePostRepositoryCustom {

    QCustomerServicePost customerServicePost = QCustomerServicePost.customerServicePost;

    public CustomerServicePostRepositoryImpl() {
        super(CustomerServicePost.class);
    }

    @Override
    public CustomerServicePost findOtoInquiry(Long inquiryId) {
        return from(customerServicePost)
                .where(customerServicePost.id.eq(inquiryId))
                .select(customerServicePost)
                .fetchOne();
    }

    @Override
    public Page<CustomerServicePost> findAllOtoInquiries(Pageable pageable, String categoryId) {
        List<CustomerServicePost> result = from(customerServicePost)
                .where(customerServicePost.category.id.eq(categoryId))
                .select(selectAllCustomerServicePostColumns())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public Page<CustomerServicePost> findAllOwnOtoInquiries(Pageable pageable, String categoryId, Long memberId) {
        List<CustomerServicePost> result = from(customerServicePost)
                .where(customerServicePost.category.id.eq(categoryId))
                .where(customerServicePost.member.id.eq(memberId))
                .select(customerServicePost)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public CustomerServicePost findOwnOtoInquiry(Long inquiryId, Long memberId) {
        return from(customerServicePost)
                .where(customerServicePost.id.eq(inquiryId))
                .where(customerServicePost.member.id.eq(memberId))
                .select(selectAllCustomerServicePostColumns())
                .fetchOne();
    }

    private ConstructorExpression<CustomerServicePost> selectAllCustomerServicePostColumns() {
        return Projections.constructor(CustomerServicePost.class,
                                       customerServicePost.id,
                                       customerServicePost.member,
                                       customerServicePost.category,
                                       customerServicePost.content,
                                       customerServicePost.title,
                                       customerServicePost.reason,
                                       customerServicePost.status,
                                       customerServicePost.createdAt,
                                       customerServicePost.updatedAt);
    }
}
