package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.entity.Order;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class OrderRepositoryImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

    public OrderRepositoryImpl() {
        super(Order.class);
    }

}
