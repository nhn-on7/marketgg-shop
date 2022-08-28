package com.nhnacademy.marketgg.server.repository.orderproduct;

import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import com.nhnacademy.marketgg.server.entity.OrderProduct;
import com.nhnacademy.marketgg.server.entity.QImage;
import com.nhnacademy.marketgg.server.entity.QOrderProduct;
import com.nhnacademy.marketgg.server.entity.QProduct;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class OrderProductRepositoryImpl extends QuerydslRepositorySupport implements OrderProductRepositoryCustom {

    public OrderProductRepositoryImpl() {
        super(OrderProduct.class);
    }

    @Override
    public List<ProductToOrder> findByOrderId(final Long orderId) {
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QProduct product = QProduct.product;
        QImage image = QImage.image;

        return from(orderProduct)
                .innerJoin(product).on(orderProduct.pk.productNo.eq(product.id))
                .innerJoin(image).on(product.asset.id.eq(image.asset.id))
                .where(orderProduct.pk.orderNo.eq(orderId))
                .select(Projections.constructor(ProductToOrder.class,
                                                orderProduct.pk.productNo,
                                                orderProduct.name,
                                                orderProduct.price,
                                                orderProduct.amount,
                                                image.imageAddress))
                .fetch();
    }

}
