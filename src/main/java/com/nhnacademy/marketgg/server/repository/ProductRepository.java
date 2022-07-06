package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

public interface ProductRepository extends JpaRepository<Product, Long> {

    ResponseEntity<Void> queryByCategoryNo(Long categoryNum);
}
