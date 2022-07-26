package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.marketgg.server.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.cart.CartRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultCartServiceTest {

    @InjectMocks
    DefaultCartService cartService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    CartRepository cartRepository;

    @Mock
    ProductRepository productRepository;

    String uuid = "UUID";
    Long productId = 1L;

    @Test
    @DisplayName("장바구니에 상품 추가")
    void testAddProduct() {
        ProductToCartRequest productAddRequest = Dummy.getDummyProductToCartRequest(productId);

        Product product = Dummy.getDummyProduct(productId);
        Member member = Dummy.getDummyMember(uuid);

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(memberRepository.findByUuid(uuid)).willReturn(Optional.of(member));

        cartService.addProduct(uuid, productAddRequest);

        verify(productRepository, times(1)).findById(productId);
        verify(memberRepository, times(1)).findByUuid(uuid);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    @DisplayName("없는 상품을 장바구니에 상품 추가")
    void testAddProductFail1() {
        ProductToCartRequest productAddRequest = Dummy.getDummyProductToCartRequest(productId);

        given(productRepository.findById(productId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.addProduct(uuid, productAddRequest))
            .isInstanceOf(ProductNotFoundException.class);
        verify(productRepository, times(1)).findById(productId);

    }

    @Test
    @DisplayName("존재하지 않는 회원의 장바구니에 상품 추가")
    void testAddProductFail2() {
        ProductToCartRequest productAddRequest = Dummy.getDummyProductToCartRequest(productId);

        Product product = Dummy.getDummyProduct();

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(memberRepository.findByUuid(uuid)).willThrow(MemberNotFoundException.class);

        assertThatThrownBy(() -> cartService.addProduct(uuid, productAddRequest))
            .isInstanceOf(MemberNotFoundException.class);
        verify(memberRepository, times(1)).findByUuid(uuid);
    }

}