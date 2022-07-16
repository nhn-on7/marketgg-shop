package com.nhnacademy.marketgg.server.repository.orderproduct;

import com.nhnacademy.marketgg.server.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProduct.Pk>, OrderProductRepositoryCustom {

}
