package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @Test
    @DisplayName("상품 생성 테스트")
    void createProductTest() {

    }

}
