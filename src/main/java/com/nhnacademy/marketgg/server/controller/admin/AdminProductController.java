package com.nhnacademy.marketgg.server.controller.admin;

import com.nhnacademy.marketgg.server.dto.request.DefaultPageRequest;
import com.nhnacademy.marketgg.server.dto.request.product.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.product.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.DefaultPageResult;
import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.service.ProductService;
import java.io.IOException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 상품 관리를 위한 RestController 입니다.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    private static final String DEFAULT_ADMIN_PRODUCT = "/admin/products";

    /**
     * 상품 생성을 위한 POST Mapping 을 지원합니다.
     *
     * @param productRequest - 상품 생성을 위한 DTO 입니다.
     * @param image          - 상품 등록시 필요한 image 입니다. MultipartFile 타입 입니다.
     * @return - Mapping URI 를 담은 응답 객체를 반환합니다.
     * @throws IOException - IOException을 던집니다.
     * @since 1.0.0
     */
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> createProduct(@RequestPart final ProductCreateRequest productRequest,
                                              @RequestPart final MultipartFile image) throws IOException {

        this.productService.createProduct(productRequest, image);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_ADMIN_PRODUCT))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 전체 상품 목록 조회를 위한 GET Mapping 을 지원합니다.
     *
     * @return - List&lt;ProductResponse&gt; 를 담은 응답 객체를 반환 합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<CommonResponse> retrieveProducts(DefaultPageRequest pageRequest) {
        DefaultPageResult<ProductResponse> productList = this.productService.retrieveProducts(pageRequest.getPageable());

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_PRODUCT))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productList);
    }

    /**
     * 상품 상세 정보 조회를 위한 GET Mapping 을 지원합니다.
     *
     * @param productId - 상품의 PK로 조회합니다.
     * @return - ProductResponse 를 담은 응답 객체를 반환 합니다.
     * @since 1.0.0
     */
    @GetMapping("/{productId}")
    public ResponseEntity<CommonResponse> retrieveProductDetails(
        @PathVariable final Long productId) {

        SingleResponse<ProductResponse> response = this.productService.retrieveProductDetails(productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_PRODUCT))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }

    /**
     * 상품 수정을 위한 PUT Mapping 을 지원합니다.
     *
     * @param productRequest - 상품 수정을 위한 DTO 입니다.
     * @param image          - 상품 수정을 위한 MultipartFile 입니다.
     * @param productId      - 상품 수정을 위한 PK 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @throws IOException - 입출력에서 문제 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@RequestPart final ProductUpdateRequest productRequest,
                                              @RequestPart final MultipartFile image,
                                              @PathVariable final Long productId) throws IOException {
        this.productService.updateProduct(productRequest, image, productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_PRODUCT + "/" + productId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 상품 소프트 삭제를 위한 POST Mapping를 지원합니다.
     * Delete Query가 날아가는 것이 아닌, 상품의 상태 값을 '삭제'로 변경 합니다.
     *
     * @param productId - 상품 삭제를 위한 PK 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId) {
        this.productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_PRODUCT + "/" + productId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 상품 상태가 삭제인 값을 복구합니다.
     *
     * @param productId - 상품 복구를 위한 기본키입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     */
    @PostMapping("/{productId}/restore")
    public ResponseEntity<Void> restoreProduct(@PathVariable final Long productId) {
        this.productService.restoreProduct(productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_PRODUCT + "/" + productId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
