package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.comsun.PageListResponse;
import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.ProductLabel;
import com.nhnacademy.marketgg.server.exception.category.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.exception.label.LabelNotFoundException;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.asset.AssetRepository;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.image.ImageRepository;
import com.nhnacademy.marketgg.server.repository.label.LabelRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.repository.productlabel.ProductLabelRepository;
import com.nhnacademy.marketgg.server.service.ProductService;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ProductLabelRepository productLabelRepository;
    private final LabelRepository labelRepository;

    private static final String dir = System.getProperty("user.home");

    @Override
    @Transactional
    public void createProduct(final ProductCreateRequest productRequest, MultipartFile imageFile)
        throws IOException {

        Asset asset = fileUpload(imageFile);

        Category category = this.categoryRepository.findById(productRequest.getCategoryCode())
                                                   .orElseThrow(CategoryNotFoundException::new);

        Product product = this.productRepository.save(new Product(productRequest, asset, category));

        ProductLabel.Pk pk = new ProductLabel.Pk(product.getId(), productRequest.getLabelNo());
        Label label = labelRepository.findById(product.getId()).orElseThrow(LabelNotFoundException::new);

        this.productLabelRepository.save(new ProductLabel(pk, product, label));
    }

    @Override
    public <T> PageListResponse<T> retrieveProducts(Pageable pageable) {
        Page<ProductResponse> products = productRepository.findAllProducts(pageable);

        Map<String, Integer> pageInfo = new HashMap<>();
        pageInfo.put("pageNum", products.getNumber());
        pageInfo.put("pageSize", products.getSize());
        pageInfo.put("totalPages", products.getTotalPages());

        return new PageListResponse(products.getContent(), pageInfo);
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

        Asset asset = fileUpload(imageFile);

        Category category = this.categoryRepository.findById(productRequest.getCategoryCode())
                                                   .orElseThrow(CategoryNotFoundException::new);

        product.updateProduct(productRequest, asset, category);
        this.productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(final Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        product.deleteProduct();
        this.productRepository.save(product);
    }

    @Override
    public void restoreProduct(final Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        product.restoreProduct();
        this.productRepository.save(product);
    }

    @Override
    public List<ProductResponse> searchProductsByName(final String keyword) {
        return this.productRepository.findByNameContaining(keyword);
    }

    @Override
    public Page<ProductResponse> searchProductByCategory(final String categoryCode, final Pageable pageable) {
        return productRepository.findByCategoryCode(categoryCode, pageable);

    }

    private Asset fileUpload(MultipartFile imageFile) throws IOException {
        File dest = new File(dir, Objects.requireNonNull(imageFile.getOriginalFilename()));
        imageFile.transferTo(dest);

        Asset asset = this.assetRepository.save(Asset.create());
        Image image = new Image(asset, dest.toString());
        this.imageRepository.save(image);

        return asset;
    }

}
