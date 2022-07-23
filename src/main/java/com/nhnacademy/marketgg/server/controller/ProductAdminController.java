package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.service.ProductService;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/shop/v1/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;

    // TODO: Develop 브랜치 머지 후 @Value값으로 고치기
    private static final String DEFAULT_PRODUCT = "/shop/v1/admin/products";


    /**
     * 상품 생성을 위한 PostMapping 을 지원합니다.
     *
     * @param productRequest - 상품 생성을 위한 DTO 입니다.
     * @param image          - 상품 등록시 필요한 image 입니다. MultipartFile 타입 입니다.
     * @return - Mapping URI 를 담은 응답 객체를 반환합니다.
     * @throws IOException
     * @since 1.0.0
     */
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE,
        MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> createProduct(
        @RequestPart final ProductCreateRequest productRequest, @RequestPart MultipartFile image)
        throws IOException {

        this.productService.createProduct(productRequest, image);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_PRODUCT))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 전체 상품 목록 조회를 위한 GetMapping 을 지원합니다.
     *
     * @return - List<ProductResponse> 를 담은 응답 객체를 반환 합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> retrieveProducts() {
        PageRequest pageRequest = PageRequest.of(0,10);
        Page<ProductResponse> productList = this.productService.retrieveProducts(pageRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_PRODUCT))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productList);
    }

    /**
     * 상품 상세 정보 조회를 위한 GetMapping 을 지원합니다.
     *
     * @param productId - 상품의 PK로 조회합니다.
     * @return - ProductResponse 를 담은 응답 객체를 반환 합니다.
     * @since 1.0.0
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> retrieveProductDetails(
        @PathVariable final Long productId) {
        ProductResponse productDetails = this.productService.retrieveProductDetails(productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_PRODUCT))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productDetails);
    }

    /**
     * 상품 수정을 위한 PutMapping 을 지원합니다.
     *
     * @param productRequest - 상품 수정을 위한 DTO 입니다.
     * @param image          - 상품 수정을 위한 MultipartFile 입니다.
     * @param productId      상품 수정을 위한 PK 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @throws IOException
     * @since 1.0.0
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(
        @RequestPart final ProductUpdateRequest productRequest, @RequestPart MultipartFile image,
        @PathVariable final Long productId) throws IOException {
        this.productService.updateProduct(productRequest, image, productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_PRODUCT + "/" + productId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 상품 소프트 삭제를 위한 PostMapping를 지원합니다.
     * Delete Query가 날아가는 것이 아닌, 상품의 상태 값을 '삭제'로 변경 합니다.
     *
     * @param productId - 상품 삭제를 위한 PK 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/{productId}/delete")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId) {
        this.productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_PRODUCT + "/" + productId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 상품 검색을 위한 GetMapping을 지원합니다.
     * 상품의 이름을 인자로 받아 해당 이름을 포함한 상품 엔티티를 검색합니다.
     *
     * @param productName - 상품 검색을 위한 String 타입 파라미터입니다.
     * @return - List<ProductResponse> 를 담은 응답 객체를 반환 합니다.
     * @since 1.0.0
     */
    @GetMapping("/search/{productName}")
    public ResponseEntity<List<ProductResponse>> searchProductsByName(
        @PathVariable final String productName) {
        List<ProductResponse> productResponseList =
            this.productService.searchProductsByName(productName);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_PRODUCT + "/search/" + productName))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productResponseList);
    }

}
