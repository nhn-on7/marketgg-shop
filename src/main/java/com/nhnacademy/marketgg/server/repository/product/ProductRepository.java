package com.nhnacademy.marketgg.server.repository.product;

import com.nhnacademy.marketgg.server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 상품 레포지토리 입니다.
 *
 * @version 1.0.0
 */
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

}
