package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.exception.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.ProductRepository;
import com.nhnacademy.marketgg.server.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createProduct(final ProductCreateRequest productRequest) {
        productRepository.save(new Product(productRequest));
    }

    @Override
    public List<ProductResponse> retrieveProducts() {
        return productRepository.findAllBy();
    }

    @Override
    @Transactional
    public void updateProduct(final ProductUpdateRequest productRequest, final Long productId) {
        Product product = productRepository
            .findById(productRequest.getProductNo())
            .orElseThrow(() -> new ProductNotFoundException("해당 상품을 찾을 수 없습니다."));

        product.updateProduct(productRequest);
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(final Long productId) {
        Product product = productRepository
            .findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("해당 상품을 찾을 수 없습니다."));

        // Review : soft delete ? 실제 삭제?
        productRepository.delete(product);
    }

}
