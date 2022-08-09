package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.request.category.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.category.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.label.LabelCreateRequest;
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
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.repository.asset.AssetRepository;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.image.ImageRepository;
import com.nhnacademy.marketgg.server.repository.label.LabelRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.repository.productlabel.ProductLabelRepository;
import com.nhnacademy.marketgg.server.service.image.ImageService;
import com.nhnacademy.marketgg.server.service.product.DefaultProductService;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultProductServiceTest {

    @InjectMocks
    DefaultProductService productService;

    @Mock
    private SearchRepository searchRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private AssetRepository assetRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private LabelRepository labelRepository;
    @Mock
    private ElasticProductRepository elasticProductRepository;
    @Mock
    private ImageService imageService;
    @Spy
    private ProductLabelRepository productLabelRepository;

    @Spy
    CategoryRepository categoryRepository;

    private static ProductCreateRequest productRequest;
    private static ProductUpdateRequest productUpdateRequest;
    private static ProductResponse productResponse;
    private static Asset asset;
    private static Category category;
    private static Label label;
    private static MockMultipartFile imageFile;
    private static ElasticProduct elasticProduct;
    private static Image image;
    private static Product product;
    private static SearchProductResponse searchProductResponse;

    @BeforeAll
    static void beforeAll() {
        productResponse =
            new ProductResponse(null, null, null, null, null, null, null, null, null, null,
                null, null, null,
                null, null, null, null, null, null, null);

        productRequest = new ProductCreateRequest();
        ReflectionTestUtils.setField(productRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(productRequest, "name", "자몽");
        ReflectionTestUtils.setField(productRequest, "content", "아침에 자몽 쥬스");
        ReflectionTestUtils.setField(productRequest, "totalStock", 100L);
        ReflectionTestUtils.setField(productRequest, "price", 2000L);
        ReflectionTestUtils.setField(productRequest, "description", "자몽주스 설명");
        ReflectionTestUtils.setField(productRequest, "unit", "1박스");
        ReflectionTestUtils.setField(productRequest, "deliveryType", "샛별배송");
        ReflectionTestUtils.setField(productRequest, "origin", "인도네시아");
        ReflectionTestUtils.setField(productRequest, "packageType", "냉장");
        ReflectionTestUtils.setField(productRequest, "allergyInfo", "새우알러지");
        ReflectionTestUtils.setField(productRequest, "labelNo", 1L);

        asset = Asset.create();
        ReflectionTestUtils.setField(asset, "id", 1L);

        CategorizationCreateRequest categorizationRequest = new CategorizationCreateRequest();

        ReflectionTestUtils.setField(categorizationRequest, "categorizationCode", "100");
        ReflectionTestUtils.setField(categorizationRequest, "name", "상품");
        ReflectionTestUtils.setField(categorizationRequest, "alias", "Products");

        Categorization categorization = new Categorization(categorizationRequest);

        CategoryCreateRequest categoryRequest = new CategoryCreateRequest();
        ReflectionTestUtils.setField(categoryRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(categoryRequest, "categorizationCode", "100");
        ReflectionTestUtils.setField(categoryRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryRequest, "sequence", 1);

        category = new Category(categoryRequest, categorization);
        image = Image.builder().build();

        LabelCreateRequest labelCreateRequest = new LabelCreateRequest();
        ReflectionTestUtils.setField(labelCreateRequest, "name", "안녕");

        label = new Label(labelCreateRequest);
        elasticProduct = new ElasticProduct(new Product(productRequest, asset, category), label, image);
        ReflectionTestUtils.setField(label, "id", 1L);

        product = new Product(productRequest, asset, category);
        ReflectionTestUtils.setField(product, "id", 1L);

        searchProductResponse = new SearchProductResponse();
    }

    @Test
    @DisplayName("상품 등록시 의존관계가 있는 asset, image, category repository 에서 모든 행위가 이루어지는지 검증 ")
    void testProductCreation() throws IOException {
        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();

        MockMultipartFile file =
            new MockMultipartFile("image", "test.png", "image/png",
                new FileInputStream(filePath));


        given(categoryRepository.findById(any())).willReturn(Optional.ofNullable(category));
        given(assetRepository.save(any(Asset.class))).willReturn(asset);
        given(productRepository.save(any(Product.class))).willReturn(product);
        given(labelRepository.findById(anyLong())).willReturn(Optional.ofNullable(label));
        given(imageRepository.findByAssetIdAndImageSequence(anyLong(), anyInt())).willReturn(Optional.ofNullable(
            image));


        productService.createProduct(productRequest, file);

        verify(productRepository, atLeastOnce()).save(any());
        verify(categoryRepository, atLeastOnce()).findById(any());
        verify(imageRepository, atLeastOnce()).saveAll(any());
        verify(assetRepository, atLeastOnce()).save(any());
        then(elasticProductRepository).should().save(any(ElasticProduct.class));
    }

    @Test
    @DisplayName("상품 등록 실패 테스트")
    void testProductCreationFailException() throws IOException {

        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();
        List<Image> images = List.of(image);

        MockMultipartFile file =
            new MockMultipartFile("image", "test.png", "image/png",
                new FileInputStream(filePath));

        given(imageService.parseImages(any(List.class), any(Asset.class))).willReturn(images);
        given(assetRepository.save(any(Asset.class))).willReturn(asset);
        given(imageRepository.saveAll(any())).willReturn(List.of());


        assertThatThrownBy(
            () -> productService.createProduct(productRequest, file)).hasMessageContaining(
            "카테고리를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("상품 목록 조회 테스트")
    void testRetrieveProducts() {
        // List<ElasticProduct> list = List.of(elasticProduct);
        // given(elasticProductRepository.findAll(PageRequest.of(0, 1))).willReturn(
        //         new PageImpl<>(list, PageRequest.of(0, 1), 1));
        List<ProductResponse> list = List.of(productResponse);
        Page<ProductResponse> page = new PageImpl<>(list, PageRequest.of(0, 1), 1);
        given(productRepository.findAllProducts(PageRequest.of(0, 1))).willReturn(page);

        DefaultPageResult<ProductResponse> productResponses =
            productService.retrieveProducts(PageRequest.of(0, 1));

        assertThat(productResponses).isNotNull();
        // then(elasticProductRepository).should().findAll(any(PageRequest.class));
        then(productRepository).should().findAllProducts(any(PageRequest.class));
    }

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void testRetrieveProductDetails() {
        given(productRepository.queryById(anyLong())).willReturn(productResponse);

        SingleResponse<ProductResponse> productResponse =
            productService.retrieveProductDetails(anyLong());

        assertThat(productResponse).isNotNull();
        verify(productRepository, atLeastOnce()).queryById(anyLong());
    }

    @Test
    @DisplayName("상품 정보 수정 성공 테스트")
    void testUpdateProductSuccess() throws IOException {

        Product product = new Product(productRequest, asset, category);

        ReflectionTestUtils.setField(product, "id", 1L);

        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();


        MockMultipartFile file =
            new MockMultipartFile("image", "test.png", "image/png",
                new FileInputStream(filePath));

        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        ReflectionTestUtils.setField(productUpdateRequest, "categoryCode", "001");

        given(assetRepository.save(any(Asset.class))).willReturn(asset);
        given(labelRepository.findById(anyLong())).willReturn(Optional.of(label));
        given(imageRepository.findByAssetIdAndImageSequence(anyLong(), anyInt())).willReturn(Optional.of(image));
        given(categoryRepository.findById(any())).willReturn(Optional.ofNullable(category));
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        productService.updateProduct(productUpdateRequest, file, 1L);

        then(productRepository).should().save(any());
        then(categoryRepository).should().findById(any());
        then(assetRepository).should().save(any());
        then(elasticProductRepository).should().save(any(ElasticProduct.class));
    }

    @Test
    @DisplayName("상품 정보 수정 실패 테스트")
    void testUpdateProductFail() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(
            () -> productService.updateProduct(productUpdateRequest, imageFile,
                1L)).hasMessageContaining(
            "상품을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("상품 삭제 성공 테스트")
    void testDeleteProductSuccess() {
        when(productRepository.findById(anyLong())).thenReturn(
            Optional.of(new Product(productRequest, asset, category)));
        willDoNothing().given(elasticProductRepository).deleteById(anyLong());

        productService.deleteProduct(anyLong());

        verify(productRepository, times(1)).save(any(Product.class));
        then(elasticProductRepository).should().deleteById(anyLong());
    }

    @Test
    @DisplayName("상품 삭제 실패 테스트")
    void testDeleteProductFail() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.deleteProduct(1L)).hasMessageContaining(
            "상품을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("카테고리 코드로 상품 목록 조회 테스트")
    void testFindProductsByCategoryCode() {
        given(elasticProductRepository.findAllByCategoryCode(any(PageRequest.class), anyString()))
            .willReturn(new PageImpl<>(List.of(elasticProduct)));

        productService.findProductByCategory(PageRequest.of(0, 1), "101");

        then(elasticProductRepository).should().findAllByCategoryCode(any(PageRequest.class), anyString());
    }

    @Test
    @DisplayName("전체 목록 내 상품 검색")
    void testSearchProductList() throws ParseException, JsonProcessingException {
        given(searchRepository.searchProductWithKeyword(any(SearchRequest.class), any())).willReturn(
            List.of(searchProductResponse));

        productService.searchProductList("hi", 0);

        then(searchRepository).should(times(1)).searchProductWithKeyword(any(SearchRequest.class), any());
    }

    @Test
    @DisplayName("카테고리 내 상품 목록 검색")
    void testSearchProductListByCategory() throws ParseException, JsonProcessingException {
        given(searchRepository.searchProductForCategory(anyString(), any(SearchRequest.class), any())).willReturn(
            List.of(searchProductResponse));

        productService.searchProductListByCategory("100", "hi", 0);

        then(searchRepository).should(times(1)).searchProductForCategory(anyString(), any(SearchRequest.class), any());
    }

    @Test
    @DisplayName("카테고리 내 지정한 가격 옵션별 상품 목록 검색")
    void testSearchProductListByPrice() throws ParseException, JsonProcessingException {
        given(searchRepository.searchProductForCategory(anyString(), any(SearchRequest.class), anyString())).willReturn(
            List.of(searchProductResponse));

        productService.searchProductListByPrice("100", "desc", "hi", 0);

        then(searchRepository).should(times(1))
                              .searchProductForCategory(anyString(), any(SearchRequest.class), anyString());
    }

}
