package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.exception.cart.CartNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.cart.CartRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultCartService implements CartService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    @Transactional
    @Override
    public void addProduct(String uuid, ProductToCartRequest productAddRequest) {
        Product product = productRepository.findById(productAddRequest.getId())
                                           .orElseThrow(ProductNotFoundException::new);

        Member member = memberRepository.findByUuid(uuid)
                                        .orElseThrow(MemberNotFoundException::new);

        Cart cart = new Cart(member, product, productAddRequest.getAmount());
        cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void updateAmount(String uuid, ProductToCartRequest productUpdateRequest) {
        Product product = productRepository.findById(productUpdateRequest.getId())
                                           .orElseThrow(ProductNotFoundException::new);

        Member member = memberRepository.findByUuid(uuid)
                                        .orElseThrow(MemberNotFoundException::new);

        Cart.Pk cartId = new Cart.Pk(member.getId(), product.getId());
        Cart cart = cartRepository.findById(cartId)
                                  .orElseThrow(CartNotFoundException::new);

        cart.updateAmount(productUpdateRequest.getAmount());
    }

}
