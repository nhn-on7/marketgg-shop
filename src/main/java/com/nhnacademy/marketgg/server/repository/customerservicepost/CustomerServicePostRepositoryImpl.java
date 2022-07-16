package com.nhnacademy.marketgg.server.repository.customerservicepost;

import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CustomerServicePostRepositoryImpl extends QuerydslRepositorySupport implements CustomerServicePostRepositoryCustom {

    public CustomerServicePostRepositoryImpl() {
        super(CustomerServicePost.class);
    }

}
