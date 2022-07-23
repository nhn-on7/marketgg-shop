package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.LabelCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.elasticrepository.EsProductRepository;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.elastic.EsProduct;
import com.nhnacademy.marketgg.server.exception.category.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.asset.AssetRepository;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.image.ImageRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.service.ProductService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;
    private final EsProductRepository esProductRepository;
    private final CategoryRepository categoryRepository;
    private final AssetRepository assetRepository;
    private final ImageRepository imageRepository;

    @Value("${uploadPath}")
    private String uploadPath;

    @Override
    @Transactional
    public void createProduct(final ProductCreateRequest productRequest, MultipartFile imageFile)
        throws IOException {

        String originalFileName = imageFile.getOriginalFilename();
        File dest = new File(uploadPath, originalFileName);
        imageFile.transferTo(dest);

        Asset asset = this.assetRepository.save(Asset.create());
        Image image = new Image(asset, dest.toString());
        this.imageRepository.save(image);

        Category category = this.categoryRepository.findById(productRequest.getCategoryCode())
                                                   .orElseThrow(CategoryNotFoundException::new);

        Product product = this.productRepository.save(new Product(productRequest, asset, category));

        // FIXME: 상품 등록 수정 시 매개변수 Label 보완 필요 (CoPark)
        esProductRepository.save(new EsProduct(product, new Label(new LabelCreateRequest()), image));

    }

    @Override
    public List<ProductResponse> retrieveProducts() {
        return productRepository.findAllProducts();
    }

    @Override
    public ProductResponse retrieveProductDetails(final Long productId) {
        return this.productRepository.queryById(productId);
    }

    @Override
    @Transactional
    public void updateProduct(final ProductUpdateRequest productRequest, MultipartFile imageFile,
                              final Long productId) throws IOException {
        Product product =
            this.productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        String originalFileName = imageFile.getOriginalFilename();
        File dest = new File(uploadPath, originalFileName);
        imageFile.transferTo(dest);

        Asset asset = this.assetRepository.save(Asset.create());
        Image image = new Image(asset, dest.toString());
        this.imageRepository.save(image);

        Category category = this.categoryRepository.findById(productRequest.getCategoryCode())
                                                   .orElseThrow(CategoryNotFoundException::new);

        product.updateProduct(productRequest, asset, category);
        Product updateProduct = this.productRepository.save(product);

        // FIXME: 상품 정보 변경 수정 시 매개변수 Label 보완 필요 (CoPark)
        esProductRepository.save(new EsProduct(updateProduct, new Label(new LabelCreateRequest()), image));
    }

    @Override
    public void deleteProduct(final Long productId) {
        Product product =
            this.productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        product.deleteProduct();
        this.productRepository.save(product);
        esProductRepository.deleteById(productId);
    }

    @Override
    public List<ProductResponse> searchProductsByName(final String keyword) {
        return this.productRepository.findByNameContaining(keyword);
    }

    @Override
    public List<ProductResponse> searchProductByCategory(final String categoryCode) {
        return productRepository.findByCategoryAndCategorizationCodes(categoryCode);

    }

}
