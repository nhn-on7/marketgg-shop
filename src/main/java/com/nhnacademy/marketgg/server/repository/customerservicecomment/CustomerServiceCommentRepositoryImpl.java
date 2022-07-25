package com.nhnacademy.marketgg.server.repository.customerservicecomment;

import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CustomerServiceCommentRepositoryImpl extends QuerydslRepositorySupport implements CustomerServiceCommentRepositoryCustom {

    public CustomerServiceCommentRepositoryImpl() {
        super(CustomerServiceComment.class);
    }


}
