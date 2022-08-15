package com.nhnacademy.marketgg.server.repository.orderproduct;

import com.nhnacademy.marketgg.server.dto.response.orderproduct.OrderProductResponse;
import com.nhnacademy.marketgg.server.entity.OrderProduct;
import com.nhnacademy.marketgg.server.entity.QOrderProduct;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class OrderProductRepositoryImpl extends QuerydslRepositorySupport implements OrderProductRepositoryCustom {

    public OrderProductRepositoryImpl() {
        super(OrderProduct.class);
    }

    @Override
    public List<OrderProductResponse> findByOrderId(final Long orderId) {
        QOrderProduct orderProduct = QOrderProduct.orderProduct;

        return from(orderProduct)
                .where(orderProduct.pk.orderNo.eq(orderId))
                .select(Projections.constructor(OrderProductResponse.class,
                                                orderProduct.pk.productNo,
                                                orderProduct.name,
                                                orderProduct.price,
                                                orderProduct.amount))
                .fetch();
    }

}
