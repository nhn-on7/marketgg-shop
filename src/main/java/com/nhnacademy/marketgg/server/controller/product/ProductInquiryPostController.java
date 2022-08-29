package com.nhnacademy.marketgg.server.controller.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.annotation.Auth;
import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.DefaultPageRequest;
import com.nhnacademy.marketgg.server.dto.request.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.service.product.ProductInquiryPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Operation(summary = "상품 문의 등록",
               description = "회원이 특정 상품에 대해 상품 문의를 등록합니다.",
               parameters = @Parameter(name = "inquiryRequest",
                                       description = "회원이 작성한 상품 문의 요청 객체", required = true),
               responses = @ApiResponse(responseCode = "201",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @Auth
    @PostMapping("/products/{productId}/inquiry")
    public ResponseEntity<ShopResult<String>> createProductInquiry(@PathVariable final Long productId,
                                                                   @Valid @RequestBody final
                                                                   ProductInquiryRequest inquiryRequest,
                                                                   final MemberInfo memberInfo) {

        productInquiryPostService.createProductInquiry(memberInfo, inquiryRequest, productId);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWithDefaultMessage());
    }

    /**
     * 한 상품에 대한 전체 상품 문의 글을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param productId - 상품 문의 글을 조회하는 상품의 PK 입니다.
     * @return - List<ProductInquiryResponse> 를 담은 응답 객체를 반환 합니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Operation(summary = "상품 문의 조회",
               description = "특정 상품에 대한 상품 문의 조회",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @Auth
    @GetMapping("/products/{productId}/inquiries")
    public ResponseEntity<ShopResult<PageEntity<ProductInquiryResponse>>> retrieveProductInquiry(
        @PathVariable final Long productId,
        @RequestParam(value = "page", defaultValue = "1") final Integer page)
        throws JsonProcessingException {

        DefaultPageRequest pageRequest = new DefaultPageRequest(page - 1);

        PageEntity<ProductInquiryResponse> productInquiryResponses
            = productInquiryPostService.retrieveProductInquiryByProductId(productId, pageRequest.getPageable());

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWith(productInquiryResponses));
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
    @Operation(summary = "상품 문의 삭제",
               description = "회원이 등록한 상품 문의를 삭제합니다.",
               responses = @ApiResponse(responseCode = "204",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @Auth
    @DeleteMapping("/products/{productId}/inquiry/{inquiryId}")
    public ResponseEntity<ShopResult<String>> deleteProductInquiry(@PathVariable final Long productId,
                                                                   @PathVariable final Long inquiryId) {

        productInquiryPostService.deleteProductInquiry(inquiryId, productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWithDefaultMessage());
    }

}
