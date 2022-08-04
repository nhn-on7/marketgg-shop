package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.service.ProductInquiryPostService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 상품 문의 관리를 위한 RestController 입니다.
 *
 * @version 1.0.0
 */

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductInquiryPostController {

    private final ProductInquiryPostService productInquiryPostService;

    /**
     * 상품 문의 등록을 위한 POST Mapping 을 지원합니다.
     *
     * @param memberInfo     - 상품 문의 등록할 회원의 정보입니다.
     * @param inquiryRequest - 상품 문의 등록을 위한 DTO 입니다.
     * @param productId      - 상품 문의 등록시 등록하는 상품의 PK 입니다.
     * @return - Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @RoleCheck(accessLevel = Role.LOGIN)
    @PostMapping("/products/{productId}/inquiries")
    public ResponseEntity<CommonResponse> createProductInquiry(final MemberInfo memberInfo,
                                                               @PathVariable final Long productId,
                                                               @Valid @RequestBody final
                                                               ProductInquiryRequest inquiryRequest) {

        productInquiryPostService.createProductInquiry(memberInfo, inquiryRequest, productId);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Add Success"));
    }

    /**
     * 한 상품에 대한 전체 상품 문의 글을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param productId - 상품 문의 글을 조회하는 상품의 PK 입니다.
     * @return - List<ProductInquiryResponse> 를 담은 응답 객체를 반환 합니다.
     * @since 1.0.0
     */
    @RoleCheck(accessLevel = Role.ROLE_USER)
    @GetMapping("/products/{productId}/inquiries")
    public ResponseEntity<CommonResponse> retrieveProductInquiry(@PathVariable final Long productId,
                                                                 final Pageable pageable) {
        Page<ProductInquiryResponse> productInquiryResponses
            = productInquiryPostService.retrieveProductInquiryByProductId(productId, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>(productInquiryResponses));
    }

    /**
     * 한 회원이 상품에 대해 문의한 전체 상품 문의 글을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param memberInfo - 상품 문의 글을 조회할 회원의 정보 입니다.
     * @return - List<ProductInquiryResponse> 를 담은 응답 객체를 반환 합니다.
     * @since 1.0.0
     */
    @RoleCheck(accessLevel = Role.LOGIN)
    @GetMapping("/members/product-inquiries")
    public ResponseEntity<CommonResponse> retrieveProductInquiry(final MemberInfo memberInfo,
                                                                 final Pageable pageable) {
        Page<ProductInquiryResponse> productInquiryResponses
            = productInquiryPostService.retrieveProductInquiryByMemberId(memberInfo, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create("/members/product-inquiries"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>(productInquiryResponses));
    }

    /**
     * 상품 문의 글 삭제를 위한 DELETE Mapping 을 지원합니다.
     *
     * @param productId - 상품의 PK 입니다.
     * @param inquiryId - 삭제할 상품 문의 글의 PK 입니다.
     * @return - Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @RoleCheck(accessLevel = Role.LOGIN)
    @DeleteMapping("/products/{productId}/inquiries/{inquiryId}")
    public ResponseEntity<CommonResponse> deleteProductInquiry(@PathVariable final Long productId,
                                                               @PathVariable final Long inquiryId) {

        productInquiryPostService.deleteProductInquiry(inquiryId, productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>("Delete Success"));
    }

}
