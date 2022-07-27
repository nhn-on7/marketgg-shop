package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.server.dto.response.CartResponse;
import java.util.List;

/**
 * 장바구니 관련 비즈니스 로직을 처리합니다.
 *
 * @version 1.0.0
 */
public interface CartService {

    /**
     * 회원이 상품을 장바구니에 담을 때 실행됩니다.
     *
     * @param uuid              - 회원의 고유 번호
     * @param productAddRequest - 장바구니에 담으려는 상품 정보
     */
    void addProduct(String uuid, ProductToCartRequest productAddRequest);

    /**
     * 회원이 장바구니에 담은 상품을 조회합니다.
     *
     * @param uuid - 회원의 고유번호
     * @return - 장바구니 목록
     */
    List<CartResponse> retrieveCarts(String uuid);

    /**
     * 장바구니에 담긴 상품 수량을 변경합니다.
     *
     * @param uuid                 - 회원의 고유번호
     * @param productUpdateRequest - 수량을 변경하려는 상품 정보
     */
    void updateAmount(String uuid, ProductToCartRequest productUpdateRequest);

    /**
     * 장바구니에 담긴 상품을 삭제합니다.
     *
     * @param uuid     - 회원의 고유번호
     * @param products - 삭제하려는 상품 목록
     */
    void deleteProducts(String uuid, List<Long> products);

}
