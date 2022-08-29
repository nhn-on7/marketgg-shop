package com.nhnacademy.marketgg.server.repository.product;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.marketgg.server.dto.request.category.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.category.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.product.ProductCreateRequest;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.repository.asset.AssetRepository;
import com.nhnacademy.marketgg.server.repository.categorization.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import java.time.LocalDate;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@ActiveProfiles(value = { "local", "testdb" })
@DataJpaTest
class ProductRepositoryImplTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategorizationRepository categorizationRepository;

    @Autowired
    private AssetRepository assetRepository;

    private Product product;
    private ProductCreateRequest productRequest;
    private Asset asset;
    private Category category;
    private Categorization categorization;

    @BeforeEach
    void setUp() {
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
        ReflectionTestUtils.setField(productRequest, "capacity", "100");
        ReflectionTestUtils.setField(productRequest, "expirationDate", LocalDate.now());

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

        asset = Asset.create();

        category = new Category(categoryRequest, categorization);
        assetRepository.save(asset);

        categorizationRepository.save(categorization);
        categoryRepository.save(category);
    }

    @Test
    @DisplayName("모든 상품이 제대로 Page에 담겨서 return되는지 테스트")
    void testFindAllProducts() {
        IntStream.rangeClosed(1, 10)
                 .forEach(i -> {
                     product = new Product(productRequest, asset, category);
                     productRepository.save(product);
                 });

//        assertThat(productRepository.findAllProducts(PageRequest.of(0, 10))).hasSize(1);
    }


    // @Test
    @DisplayName("카테고리로 상품을 찾을 수 있는지 테스트")
    void testFindByCategoryCode() {
        IntStream.rangeClosed(1, 10)
                 .forEach(i -> {
                     product = new Product(productRequest, asset, category);
                     productRepository.save(product);
                 });

        assertThat(productRepository.findByCategoryCode("001", PageRequest.of(1, 10))).hasSize(10);
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        categorizationRepository.deleteAll();
        assetRepository.deleteAll();
    }

}
