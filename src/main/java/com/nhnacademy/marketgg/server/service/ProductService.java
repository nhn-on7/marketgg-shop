package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;

public interface ProductService {

    void createProduct(ProductCreateRequest productRequest);

    void updateProduct(ProductUpdateRequest productRequest, Long productNo);
}
