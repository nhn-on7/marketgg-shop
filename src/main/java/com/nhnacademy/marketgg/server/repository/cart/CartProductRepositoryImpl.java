package com.nhnacademy.marketgg.server.repository.cart;

import com.nhnacademy.marketgg.server.dto.response.cart.CartProductResponse;
import com.nhnacademy.marketgg.server.entity.CartProduct;
import com.nhnacademy.marketgg.server.entity.QCartProduct;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CartProductRepositoryImpl extends QuerydslRepositorySupport implements CartProductRepositoryCustom {

    public CartProductRepositoryImpl() {
        super(CartProduct.class);
    }

    @Override
    public List<CartProductResponse> findCartProductsByCartId(Long cartId) {
        QCartProduct cartProduct = QCartProduct.cartProduct;

        return from(cartProduct)
            .innerJoin(cartProduct.product)
            .where(cartProduct.pk.cartId.eq(cartId))
            .select(Projections.constructor(CartProductResponse.class,
                cartProduct.product.id, cartProduct.product.name, cartProduct.amount,
                cartProduct.product.price))
            .fetch();
    }

}
