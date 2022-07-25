package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.service.ProductInquiryPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * 상품 문의 관리를 위한 RestController 입니다.
 *
 * @version 1.0.0
 */

@RestController
@RequestMapping("/shop/v1")
@RequiredArgsConstructor
public class ProductInquiryPostController {

    private final ProductInquiryPostService productInquiryPostService;

    @Value("${inquiryPath}")
    private String defaultInquiryUri;

    /**
     * 상품 문의 등록을 위한 POST Mapping 을 지원합니다.
     *
     * @param inquiryRequest - 상품 문의 등록을 위한 DTO 입니다.
     * @param productId      - 상품 문의 등록시 등록하는 상품의 PK 입니다.
     * @return - Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/products/{productId}/inquiries")
    public ResponseEntity<Void> createProductInquiry(@PathVariable final Long productId,
                                                     @RequestBody final ProductInquiryRequest inquiryRequest) {

        productInquiryPostService.createProductInquiry(inquiryRequest, productId);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/shop/v1/products/" + productId + "/inquiries"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 한 상품에 대한 전체 상품 문의 글을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param productId - 상품 문의 글을 조회하는 상품의 PK 입니다.
     * @return - List<ProductInquiryResponse> 를 담은 응답 객체를 반환 합니다.
     * @since 1.0.0
     */
    @GetMapping("/products/{productId}/inquiries")
    public ResponseEntity<List<ProductInquiryResponse>> retrieveProductInquiryByProductId(@PathVariable final Long productId,
                                                                                          Pageable pageable) {
        List<ProductInquiryResponse> productInquiryResponses = productInquiryPostService.retrieveProductInquiryByProductId(productId, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(defaultInquiryUri + "/products/" + productId + "/inquiries"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productInquiryResponses);
    }

    /**
     * 한 회원이 상품에 대해 문의한 전체 상품 문의 글을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param memberId - 회원의 PK 로 조회합니다.
     * @return - List<ProductInquiryResponse> 를 담은 응답 객체를 반환 합니다.
     * @since 1.0.0
     */
    @GetMapping("/members/{memberId}/product-inquiries")
    public ResponseEntity<List<ProductInquiryResponse>> retrieveProductInquiryByMemberId(@PathVariable final Long memberId,
                                                                                         Pageable pageable) {
        List<ProductInquiryResponse> productInquiryResponses = productInquiryPostService.retrieveProductInquiryByMemberId(memberId, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(defaultInquiryUri + "/members/" + memberId + "/product-inquiries"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productInquiryResponses);
    }

    /**
     * 상품 문의 글 삭제를 위한 DELETE Mapping 을 지원합니다.
     *
     * @param productId - 상품의 PK 입니다.
     * @param inquiryId - 삭제할 상품 문의 글의 PK 입니다.
     * @return - Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/products/{productId}/inquiries/{inquiryId}")
    public ResponseEntity<Void> deleteProductInquiry(@PathVariable final Long productId,
                                                     @PathVariable final Long inquiryId) {

        productInquiryPostService.deleteProductInquiry(inquiryId, productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(defaultInquiryUri + "/products/" + productId + "/inquiries"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
