package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.exception.ProductCreationFailedException;
import com.nhnacademy.marketgg.server.repository.ProductRepository;
import com.nhnacademy.marketgg.server.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class DefaultProductServiceTest {

    @Autowired
    ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    private ProductCreateRequest productRequest;

    @BeforeEach
    void setUp() {
        productRequest = new ProductCreateRequest();
        ReflectionTestUtils.setField(productRequest, "categoryNo", 1L);
        ReflectionTestUtils.setField(productRequest, "name", "상큼한 오렌지");
        ReflectionTestUtils.setField(productRequest, "content", "오렌지는 맛있어");
        ReflectionTestUtils.setField(productRequest, "price", 10000L);


    }

    @Test
    @DisplayName("상품 등록 성공 테스트")
    void testProductCreation() {

        when(productRepository.findById(any())).thenReturn(Optional.of(new Product(productRequest)));

        Optional<Product> product = Optional.of(new Product(productRequest));
        productService.createProduct(productRequest);

        assertThat(productRepository.findById(1L).get().getProductNo()).isEqualTo(product.get().getProductNo());
        verify(productRepository, atLeastOnce()).save(any());
    }

    @Test
    @DisplayName("상품 등록 실패 테스트")
    void testProductCreationFailException() {
        when(productRepository.save(any())).thenThrow(new ProductCreationFailedException("상품 등록에 실패하였습니다."));

        assertThatThrownBy(() -> productService.createProduct(productRequest)).hasMessageContaining(
                "상품 등록에 실패하였습니다.");
        verify(productRepository, atLeastOnce()).save(any());
    }




}
