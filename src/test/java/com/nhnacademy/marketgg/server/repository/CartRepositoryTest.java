package com.nhnacademy.marketgg.server.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.marketgg.server.dto.response.CartResponse;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.repository.asset.AssetRepository;
import com.nhnacademy.marketgg.server.repository.cart.CartRepository;
import com.nhnacademy.marketgg.server.repository.categorization.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CartRepositoryTest {

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

    @Test
    @DisplayName("회원의 장바구니 목록 조회")
    void testFindCartByMemberId() {
        String uuid = "UUID";
        Long memberId = 1L;
        Member member = Dummy.getDummyMember(uuid, memberId);

        memberRepository.save(member);
        assetRepository.save(Dummy.getDummyAsset());
        categorizationRepository.save(Dummy.getDummyCategorization());
        categoryRepository.save(Dummy.getDummyCategory());

        List<Product> productList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            productList.add(Dummy.getDummyProduct((long) i));
        }
        productRepository.saveAll(productList);

        List<Cart> cartList = new ArrayList<>();
        for (int i = 0; i < 10; i += 2) {
            cartList.add(new Cart(member, productList.get(i), i + 1));
        }
        cartRepository.saveAll(cartList);

        List<CartResponse> productsInCart = cartRepository.findCartByMemberId(memberId);

        assertThat(productsInCart).hasSize(5);
    }

}
