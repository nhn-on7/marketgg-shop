package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;

public interface ProductService {

    void createProduct(ProductCreateRequest productRequest);

}
