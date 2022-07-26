package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.server.dto.response.CartResponse;
import java.util.List;

public interface CartService {

    void addProduct(String uuid, ProductToCartRequest productAddRequest);

    List<CartResponse> retrieveCarts(String uuid);

    void updateAmount(String uuid, ProductToCartRequest productUpdateRequest);

    void deleteProducts(String uuid, List<Long> products);

}
