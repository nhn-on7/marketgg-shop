package com.nhnacademy.marketgg.server.repository.customerservicepost;

import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.QCustomerServicePost;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CustomerServicePostRepositoryImpl extends QuerydslRepositorySupport implements CustomerServicePostRepositoryCustom {

    QCustomerServicePost customerServicePost = QCustomerServicePost.customerServicePost;

    public CustomerServicePostRepositoryImpl() {
        super(CustomerServicePost.class);
    }

    @Override
    public Page<CustomerServicePost> findPostsByCategoryId(final Pageable pageable, final String categoryId) {
        QueryResults<CustomerServicePost> result = from(customerServicePost)
                .where(customerServicePost.category.id.eq(categoryId))
                .select(selectAllCustomerServicePostColumns())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<CustomerServicePost> findPostByCategoryAndMember(final Pageable pageable, final String categoryId, final Long memberId) {
        QueryResults<CustomerServicePost> result = from(customerServicePost)
                .where(customerServicePost.category.id.eq(categoryId))
                .where(customerServicePost.member.id.eq(memberId))
                .select(customerServicePost)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
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
