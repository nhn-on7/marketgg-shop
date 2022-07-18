package com.nhnacademy.marketgg.server.repository.cart;

import com.nhnacademy.marketgg.server.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Cart.Pk>, CartRepositoryCustom {

}
