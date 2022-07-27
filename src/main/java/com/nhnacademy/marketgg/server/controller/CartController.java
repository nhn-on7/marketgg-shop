package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.annotation.UUID;
import com.nhnacademy.marketgg.server.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.server.dto.response.CartResponse;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.ListResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.service.CartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 장바구니 관련 요청을 처리합니다.
 *
 * @version 1.0.0
 */
@RoleCheck(accessLevel = Role.ROLE_USER)
@RestController
@RequestMapping("/shop/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니 등록 요청을 처리합니다.
     *
     * @param uuid              - 사용자 고유 번호
     * @param productAddRequest - 장바구니에 추가하려는 상품 정보
     * @return - 성공여부 반환
     */
    @PostMapping
    public ResponseEntity<CommonResponse> addProductToCart(@UUID String uuid,
                                                           @RequestBody ProductToCartRequest productAddRequest) {

        cartService.addProduct(uuid, productAddRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Add Success"));
    }

    /**
     * 장바구니 조회 요청을 처리합니다.
     *
     * @param uuid - 사용자 고유 번호
     * @return - 성공여부 반환
     */
    @GetMapping
    public ResponseEntity<CommonResponse> retrieveCart(@UUID String uuid) {
        List<CartResponse> cartResponses = cartService.retrieveCarts(uuid);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ListResponse<>(cartResponses));
    }

    /**
     * 장바구니 등록 상품 수량 변경 요청을 처리합니다.
     *
     * @param uuid                 - 사용자 고유 번호
     * @param productUpdateRequest - 변경하려는 상품 정보
     * @return - 성공여부 반환
     */
    @PatchMapping
    public ResponseEntity<CommonResponse> updateProductAmountInCart(@UUID String uuid,
                                                                    @RequestBody
                                                                    ProductToCartRequest productUpdateRequest) {

        cartService.updateAmount(uuid, productUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Update Success"));
    }

    /**
     * 장바구니 삭제 요청을 처리합니다.
     *
     * @param uuid - 사용자 고유 번호
     * @return - 성공여부 반환
     */
    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteProducts(@UUID String uuid,
                                                         @RequestBody List<Long> products) {

        cartService.deleteProducts(uuid, products);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Delete Success"));
    }

}
