package com.nhnacademy.marketgg.server.repository.orderproduct;

import com.nhnacademy.marketgg.server.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문 상품 Repository 입니다.
 *
 * @version 1.0.0
 * @author 김정민
 */
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProduct.Pk>, OrderProductRepositoryCustom {

}
