package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    void createProduct(ProductCreateRequest productRequest, MultipartFile image) throws IOException;

    List<ProductResponse> retrieveProducts();

    ProductResponse retrieveProductDetails(Long productId);

    void updateProduct(ProductUpdateRequest productRequest, MultipartFile image, Long productId)
        throws IOException;

    void deleteProduct(Long productId);

    List<ProductResponse> searchProductsByName(String keyword);

}
