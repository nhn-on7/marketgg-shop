package com.nhnacademy.marketgg.server.repository.cart;

import com.nhnacademy.marketgg.server.dto.response.CartResponse;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.QCart;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CartRepositoryImpl extends QuerydslRepositorySupport implements CartRepositoryCustom {

    public CartRepositoryImpl() {
        super(Cart.class);
    }

    @Override
    public List<CartResponse> findCartByMemberId(Long memberId) {
        QCart cart = QCart.cart;

        return from(cart)
            .innerJoin(cart.product)
            .on(cart.id.productId.eq(cart.product.id))
            .where(cart.id.memberId.eq(memberId))
            .select(Projections.constructor(CartResponse.class,
                cart.product.id, cart.product.name, cart.amount, cart.product.price))
            .fetch();
    }
}
