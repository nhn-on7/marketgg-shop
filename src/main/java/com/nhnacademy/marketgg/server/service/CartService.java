package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.ProductToCartRequest;

public interface CartService {

    void addProduct(String uuid, ProductToCartRequest productAddRequest);

    void updateAmount(String uuid, ProductToCartRequest productUpdateRequest);

}
