package com.nhnacademy.marketgg.server.repository.cart;

import com.nhnacademy.marketgg.server.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 장바구니 정보를 DB 에 저장합니다.
 *
 * {@link com.nhnacademy.marketgg.server.repository.cart.CartRepositoryCustom}
 */
public interface CartRepository extends JpaRepository<Cart, Cart.Pk>, CartRepositoryCustom {

}
