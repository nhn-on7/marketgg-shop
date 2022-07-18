package com.nhnacademy.marketgg.server.repository.orderdeliveryaddress;

import com.nhnacademy.marketgg.server.entity.OrderDeliveryAddress;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class OrderDeliveryAddressRepositoryImpl extends QuerydslRepositorySupport implements OrderDeliveryAddressRepositoryCustom {

    public OrderDeliveryAddressRepositoryImpl() {
        super(OrderDeliveryAddress.class);
    }

}
