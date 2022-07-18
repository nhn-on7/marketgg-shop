package com.nhnacademy.marketgg.server.repository.deliveryaddress;

import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class DeliveryAddressRepositoryImpl extends QuerydslRepositorySupport implements DeliveryAddressRepositoryCustom {

    public DeliveryAddressRepositoryImpl() {
        super(DeliveryAddress.class);
    }

}
