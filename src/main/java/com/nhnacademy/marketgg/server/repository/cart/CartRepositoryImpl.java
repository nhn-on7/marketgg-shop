package com.nhnacademy.marketgg.server.repository.cart;

import com.nhnacademy.marketgg.server.entity.Cart;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CartRepositoryImpl extends QuerydslRepositorySupport implements CartRepositoryCustom {

    public CartRepositoryImpl() {
        super(Cart.class);
    }

}
