package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.repository.asset.AssetRepository;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.image.ImageRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultProductServiceTest {

    @InjectMocks
    DefaultProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private ImageRepository imageRepository;

    @Spy
    CategoryRepository categoryRepository;

    private static ProductCreateRequest productRequest;
    private static ProductUpdateRequest productUpdateRequest;
    private static ProductResponse productResponse;
    private static Asset asset;
    private static Category category;
    private static Categorization categorization;
    private static MockMultipartFile imageFile;

    @BeforeAll
    static void beforeAll() {
        productResponse = new ProductResponse(null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null,null, null, null, null);

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

        asset = Asset.create();
        ReflectionTestUtils.setField(asset, "id", 1L);

        CategorizationCreateRequest categorizationRequest = new CategorizationCreateRequest();

        ReflectionTestUtils.setField(categorizationRequest, "categorizationCode", "100");
        ReflectionTestUtils.setField(categorizationRequest, "name", "상품");
        ReflectionTestUtils.setField(categorizationRequest, "alias", "Products");

        categorization = new Categorization(categorizationRequest);

        CategoryCreateRequest categoryRequest = new CategoryCreateRequest();
        ReflectionTestUtils.setField(categoryRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(categoryRequest, "categorizationCode", "100");
        ReflectionTestUtils.setField(categoryRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryRequest, "sequence", 1);

        category = new Category(categoryRequest, categorization);
    }

    // @Test
    @DisplayName("상품 등록시 의존관계가 있는 asset, image, category repository에서 모든 행위가 이루어지는지 검증 ")
    void testProductCreation() throws IOException {
        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();

        MockMultipartFile file =
            new MockMultipartFile("image", "test.png", "image/png", new FileInputStream(filePath));

        given(assetRepository.save(any(Asset.class))).willReturn(asset);
        given(imageRepository.save(any(Image.class))).willReturn(new Image(asset, "test"));
        given(categoryRepository.findById(any())).willReturn(Optional.ofNullable(category));

        Optional<Product> product = Optional.of(new Product(productRequest, asset, category));
        productService.createProduct(productRequest, file);

        verify(productRepository, atLeastOnce()).save(any());
        verify(categoryRepository, atLeastOnce()).findById(any());
        verify(imageRepository, atLeastOnce()).save(any());
        verify(assetRepository, atLeastOnce()).save(any());

    }

    @Test
    @DisplayName("상품 등록 실패 테스트")
    void testProductCreationFailException() throws IOException {

        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();

        MockMultipartFile file =
            new MockMultipartFile("image", "test.png", "image/png", new FileInputStream(filePath));

        assertThatThrownBy(() -> productService.createProduct(productRequest, file)).hasMessageContaining(
            "카테고리를 찾을 수 없습니다.");
    }

    // @Test
    // @DisplayName("상품 목록 조회 테스트")
    // void testRetrieveProducts() {
    //     when(productRepository.findAllProducts()).thenReturn(List.of(productResponse));
    //
    //     List<ProductResponse> productResponses = productService.retrieveProducts();
    //     assertThat(productResponses).isNotNull();
    //     verify(productRepository, atLeastOnce()).findAllProducts();
    // }

    // @Test
    // @DisplayName("상품 상세 조회 테스트")
    // void testRetrieveProductDetails() {
    //     when(productRepository.queryById(anyLong())).thenReturn(productResponse);
    //
    //     ProductResponse productResponse = productService.retrieveProductDetails(anyLong());
    //     assertThat(productResponse).isNotNull();
    //     verify(productRepository, atLeastOnce()).queryById(anyLong());
    // }

    @Test
    @DisplayName("상품 정보 수정 성공 테스트")
    void testUpdateProductSuccess() throws IOException {

        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();

        MockMultipartFile file =
            new MockMultipartFile("image", "test.png", "image/png", new FileInputStream(filePath));

        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        ReflectionTestUtils.setField(productUpdateRequest, "categoryCode", "001");

        given(assetRepository.save(any(Asset.class))).willReturn(asset);
        given(imageRepository.save(any(Image.class))).willReturn(new Image(asset, "test"));
        given(categoryRepository.findById(any())).willReturn(Optional.ofNullable(category));
        given(productRepository.findById(anyLong())).willReturn(
            Optional.of(new Product(productRequest, asset, category)));

        productService.updateProduct(productUpdateRequest, file, 1L);

        verify(productRepository, atLeastOnce()).save(any());
        verify(categoryRepository, atLeastOnce()).findById(any());
        verify(imageRepository, atLeastOnce()).save(any());
        verify(assetRepository, atLeastOnce()).save(any());
    }

    @Test
    @DisplayName("상품 정보 수정 실패 테스트")
    void testUpdateProductFail() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(
            () -> productService.updateProduct(productUpdateRequest, imageFile, 1L)).hasMessageContaining(
            "상품을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("상품 삭제 성공 테스트")
    void testDeleteProductSuccess() {
        when(productRepository.findById(anyLong())).thenReturn(
            Optional.of(new Product(productRequest, asset, category)));

        productService.deleteProduct(anyLong());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 삭제 실패 테스트")
    void testDeleteProductFail() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.deleteProduct(1L)).hasMessageContaining("상품을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("상품 이름으로 상품 목록 조회")
    void testSearchProductsByName() {
        when(productRepository.findByNameContaining(anyString())).thenReturn(List.of(productResponse));

        List<ProductResponse> productResponses = productService.searchProductsByName(anyString());
        verify(productRepository, times(1)).findByNameContaining(anyString());
    }

    // @Test
    // @DisplayName("카테고리 코드로 상품 목록 조회 테스트")
    // void testSearchProductsByCategoryCode() {
    //     BDDMockito.given(productRepository.findByCategoryCode(anyString()))
    //               .willReturn(List.of(productResponse));
    //
    //     productService.searchProductByCategory("101");
    //
    //     verify(productRepository, atLeastOnce()).findByCategoryCode(anyString());
    // }

}
