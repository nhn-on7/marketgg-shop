package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.server.dto.response.CartProductResponse;
import java.util.List;

/**
 * 장바구니 관련 비즈니스 로직을 처리합니다.
 *
 * @version 1.0.0
 */
public interface CartProductService {

    /**
     * 회원이 상품을 장바구니에 담을 때 실행됩니다.
     *
     * @param member            - 회원정보
     * @param productAddRequest - 장바구니에 담으려는 상품 정보
     */
    void addProduct(final MemberInfo member, final ProductToCartRequest productAddRequest);

    /**
     * 회원이 장바구니에 담은 상품을 조회합니다.
     *
     * @param member - 회원정보
     * @return - 장바구니 목록
     */
    List<CartProductResponse> retrieveCarts(final MemberInfo member);

    /**
     * 장바구니에 담긴 상품 수량을 변경합니다.
     *
     * @param member               - 회원정보
     * @param productUpdateRequest - 수량을 변경하려는 상품 정보
     */
    void updateAmount(final MemberInfo member, final ProductToCartRequest productUpdateRequest);

    /**
     * 장바구니에 담긴 상품을 삭제합니다.
     *
     * @param member   - 회원정보
     * @param products - 삭제하려는 상품 목록
     */
    void deleteProducts(final MemberInfo member, final List<Long> products);

}
