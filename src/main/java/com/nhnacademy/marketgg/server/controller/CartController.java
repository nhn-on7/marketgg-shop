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

@RoleCheck(accessLevel = Role.ROLE_USER)
@RestController
@RequestMapping("/shop/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CommonResponse> addProductToCart(@UUID String uuid,
                                                           @RequestBody ProductToCartRequest request) {

        cartService.addProduct(uuid, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Add Success"));
    }

    @GetMapping
    public ResponseEntity<CommonResponse> retrieveCart(@UUID String uuid) {
        List<CartResponse> cartResponses = cartService.retrieveCarts(uuid);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ListResponse<>(cartResponses));
    }

    @PatchMapping
    public ResponseEntity<CommonResponse> updateProductAmountInCart(@UUID String uuid,
                                                                    @RequestBody
                                                                    ProductToCartRequest productUpdateRequest) {

        cartService.updateAmount(uuid, productUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Update Success"));
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteProducts(@UUID String uuid,
                                                         @RequestBody List<Long> products) {

        cartService.deleteProducts(uuid, products);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Delete Success"));
    }

}
