package com.nhnacademy.marketgg.server.repository.cart;

import com.nhnacademy.marketgg.server.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, CartProduct.Pk>, CartProductRepositoryCustom {

}
