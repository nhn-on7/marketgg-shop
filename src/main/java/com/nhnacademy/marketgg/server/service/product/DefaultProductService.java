package com.nhnacademy.marketgg.server.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.request.product.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.product.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.DefaultPageResult;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.dto.response.file.ImageResponse;
import com.nhnacademy.marketgg.server.dto.response.product.ProductResponse;
import com.nhnacademy.marketgg.server.elastic.document.ElasticProduct;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.dto.response.SearchProductResponse;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticProductRepository;
import com.nhnacademy.marketgg.server.elastic.repository.SearchRepository;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.ProductLabel;
import com.nhnacademy.marketgg.server.exception.category.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.exception.label.LabelNotFoundException;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.label.LabelRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.repository.productlabel.ProductLabelRepository;
import com.nhnacademy.marketgg.server.service.file.FileService;
import java.io.IOException;
import java.util.List;
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
    private final ProductLabelRepository productLabelRepository;
    private final LabelRepository labelRepository;
    private final FileService fileService;

    private final ElasticProductRepository elasticProductRepository;
    private final SearchRepository searchRepository;

    private static final Integer PAGE_SIZE = 10;

    @Transactional
    @Override
    public void createProduct(final ProductCreateRequest productRequest, final MultipartFile imageFile)
        throws IOException {

        ImageResponse imageResponse = fileService.uploadImage(imageFile);

        Category category = categoryRepository.findById(productRequest.getCategoryCode())
                                              .orElseThrow(CategoryNotFoundException::new);

        Product product =
            productRepository.save(new Product(productRequest, imageResponse.getAsset(), category));

        ProductLabel.Pk pk = new ProductLabel.Pk(product.getId(), productRequest.getLabelNo());

        Label label =
            labelRepository.findById(pk.getLabelNo()).orElseThrow(LabelNotFoundException::new);

        productLabelRepository.save(new ProductLabel(pk, product, label));
    }

    @Override
    public DefaultPageResult<ProductResponse> retrieveProducts(final Pageable pageable) {

        Page<ProductResponse> allProducts = productRepository.findAllProducts(pageable);

        return new DefaultPageResult<>(allProducts.getContent(), allProducts.getTotalElements(),
                                     allProducts.getTotalPages(), allProducts.getNumber());

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

        ImageResponse imageResponse = fileService.uploadImage(imageFile);

        Category category = categoryRepository.findById(productRequest.getCategoryCode())
                                              .orElseThrow(CategoryNotFoundException::new);

        product.updateProduct(productRequest, imageResponse.getAsset(), category);

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
