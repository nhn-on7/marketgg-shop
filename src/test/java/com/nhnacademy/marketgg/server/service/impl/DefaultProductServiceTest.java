package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.repository.AssetRepository;
import com.nhnacademy.marketgg.server.repository.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.ImageRepository;
import com.nhnacademy.marketgg.server.repository.ProductRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
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

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultProductServiceTest {

    @InjectMocks
    DefaultProductService productService;

    // TODO: @Mock 과 @MockBean의 차이는?
    @Mock
    private ProductRepository productRepository;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private ImageRepository imageRepository;

    // TODO: @Spy와 @SpyBean의 차이는?
    @Spy
    CategoryRepository categoryRepository;

    private static ProductCreateRequest productRequest;
    private static Asset asset;
    private static Category category;
    private static Categorization categorization;

    private static final String UPLOAD_PATH = "/Users/johyeonjin/Desktop/temp";

    @BeforeAll
    static void beforeAll() {
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

    @Test
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

        assertThat(productRepository.findById(1L).get().getProductNo()).isEqualTo(
            product.get().getProductNo());
        verify(productRepository, atLeastOnce()).save(any());
        verify(categoryRepository, atLeastOnce()).findById(any());
        verify(imageRepository, atLeastOnce()).save(any());
        verify(assetRepository, atLeastOnce()).save(any());

    }

    @Test
    @DisplayName("상품 등록 실패 테스트")
    void testProductCreationFailException() throws IOException {
        MockMultipartFile file = new MockMultipartFile("image", "test.png", "image/png",
            new FileInputStream(UPLOAD_PATH + "/logo.png"));

        assertThatThrownBy(
            () -> productService.createProduct(productRequest, file)).hasMessageContaining(
            "해당 카테고리 번호를 찾을 수 없습니다.");
    }


}
