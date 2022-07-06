package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.repository.ProductRepository;
import com.nhnacademy.marketgg.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createProduct(ProductCreateRequest productRequest) {
        productRepository.save(new Product(productRequest));
    }

}
