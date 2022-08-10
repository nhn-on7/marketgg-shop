package com.nhnacademy.marketgg.server.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.request.product.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.product.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.DefaultPageResult;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.dto.response.product.ProductResponse;
import com.nhnacademy.marketgg.server.elastic.document.ElasticProduct;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.dto.response.SearchProductResponse;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticProductRepository;
import com.nhnacademy.marketgg.server.elastic.repository.SearchRepository;
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
import com.nhnacademy.marketgg.server.service.file.FileService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
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
    private final FileService fileService;

    private final ElasticProductRepository elasticProductRepository;
    private final SearchRepository searchRepository;

    private static final String DIR = System.getProperty("user.home");
    private static final Integer PAGE_SIZE = 10;

    @Transactional
    @Override
    public void createProduct(final ProductCreateRequest productRequest, MultipartFile imageFile)
        throws IOException {

        Asset asset = assetRepository.save(Asset.create());
        Image image = fileService.uploadImage(imageFile, asset);
        imageRepository.save(image);

        Category category = categoryRepository.findById(productRequest.getCategoryCode())
                                              .orElseThrow(CategoryNotFoundException::new);
        Product product = productRepository.save(new Product(productRequest, asset, category));
        ProductLabel.Pk pk = new ProductLabel.Pk(product.getId(), productRequest.getLabelNo());
        Label label =
            labelRepository.findById(pk.getLabelNo()).orElseThrow(LabelNotFoundException::new);

        productLabelRepository.save(new ProductLabel(pk, product, label));
    }

    @Override
    public DefaultPageResult<ProductResponse> retrieveProducts(final Pageable pageable) {
        Page<ProductResponse> allProducts = productRepository.findAllProducts(pageable);
        return new DefaultPageResult(allProducts.getContent(), allProducts.getTotalElements(),
                                     allProducts.getTotalPages(), allProducts.getNumber());

        // return new SingleResponse<>(elasticProductRepository.findAll(pageable));
    }

    @Override
    public SingleResponse<ProductResponse> retrieveProductDetails(final Long productId) {
        return new SingleResponse<>(productRepository.queryById(productId));
    }

    @Transactional
    @Override
    public void updateProduct(final ProductUpdateRequest productRequest, MultipartFile imageFile,
                              final Long productId) throws IOException {

        Product product =
            productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        Asset asset = fileUpload(imageFile);

        Category category = categoryRepository.findById(productRequest.getCategoryCode())
                                              .orElseThrow(CategoryNotFoundException::new);

        product.updateProduct(productRequest, asset, category);

        productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteProduct(final Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        product.deleteProduct();
        productRepository.save(product);
        elasticProductRepository.deleteById(id);
    }

    @Override
    public void restoreProduct(final Long id) {
        Product product =
            this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        product.restoreProduct();
        productRepository.save(product);
    }

    @Override
    public List<ElasticProduct> findProductByCategory(final Pageable pageable,
                                                      final String categoryCode) {

        return this.elasticProductRepository.findAllByCategoryCode(pageable, categoryCode).getContent();
    }

    private Asset fileUpload(MultipartFile imageFile) throws IOException {
        File dest = new File(DIR, Objects.requireNonNull(imageFile.getOriginalFilename()));
        imageFile.transferTo(dest);

        return this.assetRepository.save(Asset.create());
    }

    @Override
    public List<SearchProductResponse> searchProductList(final String keyword, final Integer page)
        throws ParseException, JsonProcessingException {
        return searchRepository.searchProductWithKeyword(new SearchRequest(keyword, page, PAGE_SIZE), null);
    }

    @Override
    public List<SearchProductResponse> searchProductListByCategory(final String categoryId,
                                                                   final String keyword,
                                                                   final Integer page)
        throws ParseException, JsonProcessingException {

        return searchRepository.searchProductForCategory(categoryId,
                                                         new SearchRequest(keyword, page, PAGE_SIZE),
                                                         null);
    }

    @Override
    public List<SearchProductResponse> searchProductListByPrice(final String categoryId, final String option,
                                                                final String keyword, final Integer page)
        throws ParseException, JsonProcessingException {

        return searchRepository.searchProductForCategory(categoryId,
                                                         new SearchRequest(keyword, page, PAGE_SIZE),
                                                         option);
    }

}
