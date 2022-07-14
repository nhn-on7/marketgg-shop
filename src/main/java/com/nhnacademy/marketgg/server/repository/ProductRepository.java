package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<ProductResponse> findAllBy();

    ProductResponse queryByProductNo(Long productId);

    List<ProductResponse> findByNameContaining(String keyword);

    List<ProductResponse> findByCategory_CategoryCodeAndCategory_Categorization_CategorizationCode(String categorizationCode,String categoryCode);

}
