package com.nhnacademy.marketgg.server.controller.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.dto.response.SearchProductResponse;
import com.nhnacademy.marketgg.server.service.product.ProductService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 상품 Controller 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private static final String DEFAULT_PRODUCT_URI = "products";

    /**
     * 전체 목록에서 검색한 상품 목록을 반환합니다.
     *
     * @return 전체 목록에서 검색한 상품 목록을 반환합니다.
     * @throws ParseException          파싱 도중 예외 처리입니다.
     * @throws JsonProcessingException Json 과 관련된 예외 처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/search")
    public ResponseEntity<List<SearchProductResponse>> searchProductList(
            @Valid @RequestBody final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException {

        List<SearchProductResponse> productList = productService.searchProductList(searchRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_PRODUCT_URI + "/search"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productList);
    }

    /**
     * 카테고리 목록에서 검색한 상품 목록을 반환합니다.
     *
     * @param categoryId    - 지정한 카테고리 식별번호입니다.
     * @param searchRequest - 검색을 진행할 정보입니다.
     * @return 카테고리 목록에서 검색한 상품 목록을 반환합니다.
     * @throws ParseException          파싱 도중 예외 처리입니다.
     * @throws JsonProcessingException Json 과 관련된 예외 처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/categories/{categoryId}/search")
    public ResponseEntity<List<SearchProductResponse>> searchProductListByCategory(
            @PathVariable @NotBlank @Size(min = 1, max = 6) final String categoryId,
            @Valid @RequestBody final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException {

        List<SearchProductResponse> productList =
                productService.searchProductListByCategory(searchRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                     DEFAULT_PRODUCT_URI + "/categories/" + categoryId + "/search"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productList);
    }

    /**
     * 카테고리 목록내에서 선택한 가격 정렬 옵션으로 정렬된 상품 목록을 반환합니다.
     *
     * @param categoryId    - 지정한 카테고리 식별번호입니다.
     * @param option        - 검색한 목록을 정렬할 가격옵션을 정렬 값입니다.
     * @param searchRequest - 검색을 진행할 정보입니다.
     * @return 카테고리 목록내에서 선택한 가격 정렬 옵션으로 정렬된 상품 목록을 반환합니다.
     * @throws ParseException          파싱 도중 예외 처리입니다.
     * @throws JsonProcessingException Json 과 관련된 예외 처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/categories/{categoryId}/sort-price/{option}/search")
    public ResponseEntity<List<SearchProductResponse>> searchProductListByPrice(
            @PathVariable @NotBlank @Size(min = 1, max = 6) final String categoryId,
            @PathVariable @NotBlank @Min(1) final String option,
            @Valid @RequestBody final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException {

        List<SearchProductResponse> productList =
                productService.searchProductListByPrice(option, searchRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                     DEFAULT_PRODUCT_URI + "/categories/" + categoryId + "/sort-price/" + option))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productList);
    }

}
