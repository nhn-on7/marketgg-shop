package com.nhnacademy.marketgg.server.service.impl;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.product.ProductToCartRequest;
import com.nhnacademy.marketgg.server.dto.response.cart.CartProductResponse;
import com.nhnacademy.marketgg.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.CartProduct;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.exception.cart.CartNotFoundException;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.cart.CartProductRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.service.cart.DefaultCartProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultCartProductServiceTest {

    @InjectMocks
    DefaultCartProductService cartProductService;

    @Mock
    CartProductRepository cartProductRepository;

    @Mock
    ProductRepository productRepository;

    Long productId = 1L;
    Long memberId = 1L;
    Long cartId = 1L;

    @Test
    @DisplayName("장바구니에 상품 추가")
    void testAddProduct() {
        ProductToCartRequest productAddRequest = Dummy.getDummyProductToCartRequest(productId);

        Product product = Dummy.getDummyProduct(productId);
        Cart cart = Dummy.getDummyCart(cartId);
        MemberInfo member = Dummy.getDummyMemberInfo(memberId, cart);

        given(productRepository.findById(productId)).willReturn(Optional.of(product));

        cartProductService.addProduct(member, productAddRequest);

        then(productRepository).should(times(1)).findById(productId);
        then(cartProductRepository).should(times(1)).save(any(CartProduct.class));
    }

    @Test
    @DisplayName("없는 상품을 장바구니에 상품 추가")
    void testAddProductFail1() {
        ProductToCartRequest productAddRequest = Dummy.getDummyProductToCartRequest(productId);
        Cart cart = Dummy.getDummyCart(1L);
        MemberInfo member = Dummy.getDummyMemberInfo(memberId, cart);

        given(productRepository.findById(productId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> cartProductService.addProduct(member, productAddRequest))
                .isInstanceOf(ProductNotFoundException.class);

        then(productRepository).should(times(1)).findById(productId);
    }

    @Test
    @DisplayName("장바구니 조회")
    void testRetrieveCarts() {
        Cart cart = Dummy.getDummyCart(cartId);
        MemberInfo member = Dummy.getDummyMemberInfo(memberId, cart);
        given(cartProductRepository.findCartProductsByCartId(cart.getId())).willReturn(new ArrayList<>());

        List<CartProductResponse> cartResponses = cartProductService.retrieveCarts(member);

        then(cartProductRepository).should(times(1)).findCartProductsByCartId(member.getId());

        assertThat(cartResponses).isNotNull();
    }

    @Test
    @DisplayName("장바구니에 담긴 상품 수량 변경")
    void testUpdateAmount() {
        ProductToCartRequest productUpdateRequest = Dummy.getDummyProductToCartRequest(productId);

        CartProduct cartProduct = spy(Dummy.getCartProduct(cartId, productId, 1));
        Cart cart = Dummy.getDummyCart(cartId);
        MemberInfo member = Dummy.getDummyMemberInfo(memberId, cart);

        given(cartProductRepository.findById(any(CartProduct.Pk.class))).willReturn(Optional.of(cartProduct));

        cartProductService.updateAmount(member, productUpdateRequest);

        then(cartProductRepository).should(times(1)).findById(any(CartProduct.Pk.class));
        then(cartProduct).should(times(1)).updateAmount(productUpdateRequest.getAmount());

        assertThat(cartProduct.getAmount()).isEqualTo(productUpdateRequest.getAmount());
    }

    @Test
    @DisplayName("장바구니에 없는 상품 수량 변경")
    void testUpdateAmountFail() {
        ProductToCartRequest productUpdateRequest = Dummy.getDummyProductToCartRequest(productId);

        Cart cart = Dummy.getDummyCart(cartId);
        MemberInfo member = Dummy.getDummyMemberInfo(memberId, cart);

        given(cartProductRepository.findById(any(CartProduct.Pk.class)))
                .willReturn(Optional.ofNullable(any(CartProduct.class)));

        assertThatThrownBy(() -> cartProductService.updateAmount(member, productUpdateRequest))
                .isInstanceOf(CartNotFoundException.ProductInCartNotFoundException.class);

        then(cartProductRepository).should(times(1)).findById(any(CartProduct.Pk.class));
    }

    @Test
    @DisplayName("장바구니에 담긴 상품 삭제")
    void testDeleteProducts() {
        List<Long> productIds = LongStream.rangeClosed(1, 10)
                                          .boxed()
                                          .collect(toList());

        Cart cart = Dummy.getDummyCart(cartId);
        MemberInfo member = Dummy.getDummyMemberInfo(memberId, cart);

        willDoNothing().given(cartProductRepository).deleteAll(anyList());

        cartProductService.deleteProducts(member, productIds);

        then(cartProductRepository).should(times(1)).findAllById(anyList());
        then(cartProductRepository).should(times(1)).deleteAll(anyList());
    }

}
