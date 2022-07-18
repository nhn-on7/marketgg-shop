package com.nhnacademy.marketgg.server.repository.orderproduct;

import com.nhnacademy.marketgg.server.entity.OrderProduct;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class OrderProductRepositoryImpl extends QuerydslRepositorySupport implements OrderProductRepositoryCustom {

    public OrderProductRepositoryImpl() {
        super(OrderProduct.class);
    }

}
