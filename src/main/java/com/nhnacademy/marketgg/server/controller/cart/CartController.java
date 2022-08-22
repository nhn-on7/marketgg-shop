package com.nhnacademy.marketgg.server.controller.cart;

import com.nhnacademy.marketgg.server.annotation.Auth;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.product.ProductToCartRequest;
import com.nhnacademy.marketgg.server.dto.response.cart.CartProductResponse;
import com.nhnacademy.marketgg.server.service.cart.CartProductService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 장바구니 관련 요청을 수행합니다.
 */
@Auth
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartProductService cartProductService;

    /**
     * 장바구니 등록 요청을 처리합니다.
     *
     * @param member            - 요청한 사용자 정보
     * @param productAddRequest - 장바구니에 추가하려는 상품 정보
     * @return - 성공여부 반환
     */
    @PostMapping
    public ResponseEntity<ShopResult<String>> addProductToCart(MemberInfo member,
                                                               @RequestBody @Valid
                                                               ProductToCartRequest productAddRequest) {

        cartProductService.addProduct(member, productAddRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWith("Add Success"));
    }

    /**
     * 장바구니 조회 요청을 처리합니다.
     *
     * @param member - 요청한 사용자 정보
     * @return - 성공여부 반환
     */
    @GetMapping
    public ResponseEntity<ShopResult<List<CartProductResponse>>> retrieveCart(MemberInfo member) {
        List<CartProductResponse> data = cartProductService.retrieveCarts(member);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWith(data));
    }

    /**
     * 장바구니 등록 상품 수량 변경 요청을 처리합니다.
     *
     * @param member               - 요청한 사용자 정보
     * @param productUpdateRequest - 변경하려는 상품 정보
     * @return - 성공여부 반환
     */
    @PatchMapping
    public ResponseEntity<ShopResult<String>> updateAmount(MemberInfo member,
                                                           @RequestBody @Validated
                                                           ProductToCartRequest productUpdateRequest) {

        cartProductService.updateAmount(member, productUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWith("Update Success"));
    }

    /**
     * 장바구니 삭제 요청을 처리합니다.
     *
     * @param member - 요청한 사용자 정보
     * @return - 성공여부 반환
     */
    @DeleteMapping
    public ResponseEntity<ShopResult<String>> deleteProductInCart(MemberInfo member, @RequestBody List<Long> products) {
        cartProductService.deleteProducts(member, products);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWith("Delete Success"));
    }

}
