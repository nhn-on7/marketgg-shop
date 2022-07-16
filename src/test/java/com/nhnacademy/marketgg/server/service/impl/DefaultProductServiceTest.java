package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.entity.*;
import com.nhnacademy.marketgg.server.repository.AssetRepository;
import com.nhnacademy.marketgg.server.repository.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.ImageRepository;
import com.nhnacademy.marketgg.server.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    private static final String UPLOAD_PATH = "/Users/johyeonjin/Desktop/temp";

    @BeforeAll
    static void beforeAll() throws IOException {

        // REVIEW: Projection Interface 타입으로 받으면 다 Override 해야한다.
        // REVIEW: Interface 타입으로 받으면 유연하게 데이터 받을 수 있고 계층 구조 만들 수 있다.
        // REVIEW: Class or interface 타입 받는 상황에 맞게 선택해야 한다.
        productResponse = new ProductResponse() {

            @Override
            public Long getId() {
                return null;
            }

            @Override
            public Asset getAsset() {
                return null;
            }

            @Override
            public Category getCategory() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getContent() {
                return null;
            }

            @Override
            public Long getTotalStock() {
                return null;
            }

            @Override
            public Long getPrice() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getUnit() {
                return null;
            }

            @Override
            public String getDeliveryType() {
                return null;
            }

            @Override
            public String getOrigin() {
                return null;
            }

            @Override
            public String getPackageType() {
                return null;
            }

            @Override
            public LocalDate getExpirationDate() {
                return null;
            }

            @Override
            public String getAllergyInfo() {
                return null;
            }

            @Override
            public String getCapacity() {
                return null;
            }

            @Override
            public LocalDateTime getCreatedAt() {
                return null;
            }

            @Override
            public LocalDateTime getUpdatedAt() {
                return null;
            }

            @Override
            public LocalDateTime getDeletedAt() {
                return null;
            }
        };

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
        ReflectionTestUtils.setField(asset, "assetNo", 1L);

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
        given(productRepository.findById(any())).willReturn(
                Optional.of(new Product(productRequest, asset, category)));

        MockMultipartFile file = new MockMultipartFile("image", "test.png", "image/png",
                new FileInputStream(UPLOAD_PATH + "/logo.png"));

        given(assetRepository.save(any(Asset.class))).willReturn(asset);
        given(imageRepository.save(any(Image.class))).willReturn(new Image(asset, "test"));
        given(categoryRepository.findById(any())).willReturn(Optional.ofNullable(category));

        Optional<Product> product = Optional.of(new Product(productRequest, asset, category));
        productService.createProduct(productRequest, file);

        assertThat(productRepository.findById(1L).get().getId()).isEqualTo(
                product.get().getId());
        verify(productRepository, atLeastOnce()).save(any());
        verify(categoryRepository, atLeastOnce()).findById(any());
        verify(imageRepository, atLeastOnce()).save(any());
        verify(assetRepository, atLeastOnce()).save(any());

    }

    // @Test
    @DisplayName("상품 등록 실패 테스트")
    void testProductCreationFailException() throws IOException {
        MockMultipartFile file = new MockMultipartFile("image", "test.png", "image/png",
                new FileInputStream(UPLOAD_PATH + "/logo.png"));

        assertThatThrownBy(
                () -> productService.createProduct(productRequest, file)).hasMessageContaining(
                "해당 카테고리 번호를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("상품 목록 조회 테스트")
    void testRetrieveProducts() {
        when(productRepository.findAllBy()).thenReturn(List.of(productResponse));

        List<ProductResponse> productResponses = productService.retrieveProducts();
        assertThat(productResponses).isNotNull();
        verify(productRepository, atLeastOnce()).findAllBy();
    }

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void testRetrieveProductDetails() {
        when(productRepository.queryById(anyLong())).thenReturn(productResponse);

        ProductResponse productResponse = productService.retrieveProductDetails(anyLong());
        assertThat(productResponse).isNotNull();
        verify(productRepository, atLeastOnce()).queryById(anyLong());
    }

    // @Test
    @DisplayName("상품 정보 수정 성공 테스트")
    void testUpdateProductSuccess() throws IOException {

        given(productRepository.findById(any())).willReturn(
                Optional.of(new Product(productRequest, asset, category)));
        given(categoryRepository.findById(any())).willReturn(Optional.ofNullable(category));
        given(assetRepository.save(any(Asset.class))).willReturn(asset);

        productService.updateProduct(productUpdateRequest, imageFile, 1L);

        verify(productRepository, atLeastOnce()).save(any());
    }

    // @Test
    @DisplayName("상품 정보 수정 실패 테스트")
    void testUpdateProductFail() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateProduct(productUpdateRequest, imageFile,
                1L)).hasMessageContaining("해당 상품을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("상품 삭제 성공 테스트")
    void testDeleteProductSuccess() {
        when(productRepository.findById(anyLong())).thenReturn(
                Optional.of(new Product(productRequest, asset, category)));

        productService.deleteProduct(anyLong());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    // @Test
    @DisplayName("상품 삭제 실패 테스트")
    void testDeleteProductFail() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.deleteProduct(1L)).hasMessageContaining(
                "해당 상품을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("상품 이름으로 상품 목록 조회")
    void testSearchProductsByName() {
        when(productRepository.findByNameContaining(anyString())).thenReturn(
                List.of(productResponse));

        List<ProductResponse> productResponses = productService.searchProductsByName(anyString());
        verify(productRepository, times(1)).findByNameContaining(anyString());
    }
}

