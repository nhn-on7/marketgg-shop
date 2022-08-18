package com.nhnacademy.marketgg.server.controller.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.annotation.Auth;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryByProductResponse;
import com.nhnacademy.marketgg.server.service.product.ProductInquiryPostService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
 * @author 민아영
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
     * @author 민아영
     * @since 1.0.0
     */
    @Auth
    @PostMapping("/products/{productId}/inquiry")
    public ResponseEntity<ShopResult<Void>> createProductInquiry(@PathVariable final Long productId,
                                                                 @Valid @RequestBody final
                                                                 ProductInquiryRequest inquiryRequest,
                                                                 final MemberInfo memberInfo) {

        productInquiryPostService.createProductInquiry(memberInfo, inquiryRequest, productId);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.success());
    }

    /**
     * 한 상품에 대한 전체 상품 문의 글을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param productId - 상품 문의 글을 조회하는 상품의 PK 입니다.
     * @return - List<ProductInquiryResponse> 를 담은 응답 객체를 반환 합니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Auth
    @GetMapping("/products/{productId}/inquiries")
    public ResponseEntity<ShopResult<List<ProductInquiryByProductResponse>>> retrieveProductInquiry(@PathVariable final Long productId,
                                                                                                    final Pageable pageable)
        throws JsonProcessingException {

        List<ProductInquiryByProductResponse> productInquiryResponses
            = productInquiryPostService.retrieveProductInquiryByProductId(productId, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.success(productInquiryResponses));
    }

    /**
     * 상품 문의 글 삭제를 위한 DELETE Mapping 을 지원합니다.
     *
     * @param productId - 상품의 PK 입니다.
     * @param inquiryId - 삭제할 상품 문의 글의 PK 입니다.
     * @return - Mapping URI 를 담은 응답 객체를 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Auth
    @DeleteMapping("/products/{productId}/inquiry/{inquiryId}")
    public ResponseEntity<ShopResult<Void>> deleteProductInquiry(@PathVariable final Long productId,
                                                                 @PathVariable final Long inquiryId) {

        productInquiryPostService.deleteProductInquiry(inquiryId, productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.success());
    }

}
