package com.nhnacademy.marketgg.server.service.impl;

import static java.util.stream.Collectors.toList;

import com.nhnacademy.marketgg.server.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.server.dto.response.CartResponse;
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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 장바구니 로직을 처리하려는 구현체입니다.
 * <p>
 * {@link com.nhnacademy.marketgg.server.service.CartService}
 */
@Service
@RequiredArgsConstructor
public class DefaultCartService implements CartService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    @Transactional
    @Override
    public void addProduct(final String uuid, final ProductToCartRequest productAddRequest) {
        Product product = productRepository.findById(productAddRequest.getId())
                                           .orElseThrow(ProductNotFoundException::new);

        Member member = memberRepository.findByUuid(uuid)
                                        .orElseThrow(MemberNotFoundException::new);

        Cart cart = new Cart(member, product, productAddRequest.getAmount());
        cartRepository.save(cart);
    }

    @Override
    public List<CartResponse> retrieveCarts(final String uuid) {
        Member member = memberRepository.findByUuid(uuid)
                                        .orElseThrow(MemberNotFoundException::new);

        return cartRepository.findCartByMemberId(member.getId());
    }

    @Transactional
    @Override
    public void updateAmount(final String uuid, final ProductToCartRequest productUpdateRequest) {
        Product product = productRepository.findById(productUpdateRequest.getId())
                                           .orElseThrow(ProductNotFoundException::new);

        Member member = memberRepository.findByUuid(uuid)
                                        .orElseThrow(MemberNotFoundException::new);

        Cart.Pk cartId = new Cart.Pk(member.getId(), product.getId());
        Cart cart = cartRepository.findById(cartId)
                                  .orElseThrow(CartNotFoundException::new);

        cart.updateAmount(productUpdateRequest.getAmount());
    }

    @Transactional
    @Override
    public void deleteProducts(final String uuid, final List<Long> products) {
        Member member = memberRepository.findByUuid(uuid)
                                        .orElseThrow(MemberNotFoundException::new);

        List<Cart> carts =
            products.stream()
                    .map(
                        productId -> cartRepository.findById(new Cart.Pk(member.getId(), productId))
                                                   .orElseThrow(CartNotFoundException::new))
                    .collect(toList());

        cartRepository.deleteAll(carts);
    }

}
