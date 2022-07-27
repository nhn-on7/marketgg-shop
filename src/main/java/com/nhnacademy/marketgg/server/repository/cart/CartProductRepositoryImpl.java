package com.nhnacademy.marketgg.server.repository.cart;

import com.nhnacademy.marketgg.server.entity.CartProduct;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CartProductRepositoryImpl extends QuerydslRepositorySupport implements CartProductRepositoryCustom {

    public CartProductRepositoryImpl() {
        super(CartProduct.class);
    }

}
