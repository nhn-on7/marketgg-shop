package com.nhnacademy.marketgg.server.repository.cart;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.response.cart.CartProductResponse;
import com.nhnacademy.marketgg.server.dto.response.file.ImageResponse;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.CartProduct;
import com.nhnacademy.marketgg.server.entity.Image;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.repository.asset.AssetRepository;
import com.nhnacademy.marketgg.server.repository.categorization.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.image.ImageRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CartProductRepositoryTest {

    @Autowired
    CartProductRepository cartProductRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    CategorizationRepository categorizationRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ImageRepository imageRepository;

    @Test
    @DisplayName("회원의 장바구니 목록 조회")
    void testFindCartByMemberId() {
        Cart cart = cartRepository.save(new Cart());
        // Member member = memberRepository.save(Dummy.getDummyMember(cart));

        List<Product> productList = new ArrayList<>();
        categorizationRepository.save(Dummy.getDummyCategorization());
        categoryRepository.save(Dummy.getDummyCategory());
        for (int i = 1; i <= 10; i++) {
            Asset asset = assetRepository.save(Dummy.getDummyAsset((long) i));
            Image image = Image.builder()
                               .asset(asset)
                               .imageAddress("www.google.com/" + i)
                               .name("image" + i)
                               .type("png")
                               .classification("image")
                               .build();
            imageRepository.save(image);
            Product product = Dummy.getDummyProduct(asset.getId());
            productRepository.save(product);
            productList.add(product);
        }

        for (int i = 0; i < 10; i += 2) {
            CartProduct cartProduct = new CartProduct(cart, productList.get(i), i + 1);
            cartProductRepository.save(cartProduct);
        }

        List<CartProductResponse> cartProductsByCartId = cartProductRepository.findCartProductsByCartId(cart.getId());

        assertThat(cartProductsByCartId).isNotNull()
                                        .hasSize(5);

        cartProductsByCartId.forEach(c -> {
            Product product = productRepository.findById(c.getId()).orElseThrow();
            ImageResponse byAssetId = imageRepository.findByAssetId(product.getAsset().getId());

            assertThat(byAssetId).isNotNull();
        });
    }

}
