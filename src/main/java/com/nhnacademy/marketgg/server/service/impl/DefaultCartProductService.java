package com.nhnacademy.marketgg.server.service.impl;

import static java.util.stream.Collectors.toList;

import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.server.dto.response.CartProductResponse;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.CartProduct;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.exception.cart.CartNotFoundException;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.cart.CartProductRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.service.CartProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 장바구니 로직을 처리하려는 구현체입니다.
 *
 * {@link com.nhnacademy.marketgg.server.service.CartProductService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultCartProductService implements CartProductService {

    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;

    @Override
    public void addProduct(MemberInfo member, ProductToCartRequest productAddRequest) {
        Cart cart = member.getCart();
        Product product = productRepository.findById(productAddRequest.getId())
                                           .orElseThrow(ProductNotFoundException::new);

        cartProductRepository.save(new CartProduct(cart, product, productAddRequest.getAmount()));
    }

    @Override
    public List<CartProductResponse> retrieveCarts(MemberInfo member) {
        return cartProductRepository.findCartProductsByCartId(member.getCart().getId());
    }

    @Override
    public void updateAmount(MemberInfo member, ProductToCartRequest productUpdateRequest) {
        CartProduct cartProduct =
            cartProductRepository.findById(new CartProduct.Pk(member.getCart().getId(), productUpdateRequest.getId()))
                                 .orElseThrow(CartNotFoundException.ProductInCartNotFoundException::new);

        cartProduct.updateAmount(productUpdateRequest.getAmount());
    }

    @Override
    public void deleteProducts(MemberInfo member, List<Long> products) {
        List<CartProduct.Pk> cartProductPk = products.stream()
                                                     .map(
                                                         productId -> new CartProduct.Pk(member.getCart().getId(),
                                                             productId))
                                                     .collect(toList());
        List<CartProduct> cartProducts = cartProductRepository.findAllById(cartProductPk);
        cartProductRepository.deleteAll(cartProducts);
    }

}
