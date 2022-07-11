package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.exception.AssetNotFoundException;
import com.nhnacademy.marketgg.server.exception.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.exception.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.AssetRepository;
import com.nhnacademy.marketgg.server.repository.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.ImageRepository;
import com.nhnacademy.marketgg.server.repository.ProductRepository;
import com.nhnacademy.marketgg.server.service.ProductService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final AssetRepository assetRepository;

    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public void createProduct(final ProductCreateRequest productRequest, MultipartFile imageFile) throws IOException {

        String originalFileName = imageFile.getOriginalFilename();
        File dest = new File("/Users/coalong/gh-repos/marketgg/marketgg-server/src/main/resources/static", originalFileName);
        imageFile.transferTo(dest);

        Asset asset = assetRepository.save(Asset.create());
        Image image = new Image(asset, dest.toString());
        imageRepository.save(image);

        Category category = categoryRepository
                .findById(productRequest.getCategoryCode())
                .orElseThrow(() -> new CategoryNotFoundException("해당 카테고리 번호를 찾을 수 없습니다."));

        productRepository.save(new Product(productRequest, asset, category));
    }

    @Override
    public List<ProductResponse> retrieveProducts() {
        return productRepository.findAllBy();
    }

    @Override
    public ProductResponse retrieveProductDetails(Long productId) {
        return productRepository.queryByProductNo(productId);
    }

    @Override
    @Transactional
    public void updateProduct(final ProductUpdateRequest productRequest, final Long productId) {
        Product product = productRepository
            .findById(productRequest.getProductNo())
            .orElseThrow(() -> new ProductNotFoundException("해당 상품을 찾을 수 없습니다."));
        Asset asset = assetRepository
                .findById(productRequest.getAssetNo())
                .orElseThrow(() -> new AssetNotFoundException("해당 자산 번호를 찾을 수 없습니다."));
        Category category = categoryRepository
                .findById(productRequest.getCategoryCode())
                .orElseThrow(() -> new CategoryNotFoundException("해당 카테고리 번호를 찾을 수 없습니다."));
        product.updateProduct(productRequest, asset, category);
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(final Long productId) {
        Product product = productRepository
            .findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("해당 상품을 찾을 수 없습니다."));

        product.deleteProduct();
        productRepository.save(product);
    }

}
