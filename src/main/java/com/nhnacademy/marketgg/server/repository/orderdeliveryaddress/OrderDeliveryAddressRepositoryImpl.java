package com.nhnacademy.marketgg.server.repository.orderdeliveryaddress;

import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.orderdeliveryaddress.OrderDeliveryAddressResponse;
import com.nhnacademy.marketgg.server.entity.OrderDeliveryAddress;
import com.nhnacademy.marketgg.server.entity.QOrderDeliveryAddress;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class OrderDeliveryAddressRepositoryImpl extends QuerydslRepositorySupport implements OrderDeliveryAddressRepositoryCustom {

    public OrderDeliveryAddressRepositoryImpl() {
        super(OrderDeliveryAddress.class);
    }

    @Override
    public OrderDeliveryAddressResponse findByOrderId(Long orderId) {
        QOrderDeliveryAddress orderDeliveryAddress = QOrderDeliveryAddress.orderDeliveryAddress;

        return from(orderDeliveryAddress)
                .where(orderDeliveryAddress.pk.orderNo.eq(orderId))
                .select(Projections.constructor(OrderDeliveryAddressResponse.class,
                                                orderDeliveryAddress.zipCode,
                                                orderDeliveryAddress.address,
                                                orderDeliveryAddress.detailAddress))
                .fetchOne();
    }

}
