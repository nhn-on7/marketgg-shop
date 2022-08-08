package com.nhnacademy.marketgg.server.controller.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.elastic.document.ElasticProduct;
import com.nhnacademy.marketgg.server.elastic.dto.response.SearchProductResponse;
import com.nhnacademy.marketgg.server.service.ProductService;

import java.net.URI;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 상품 Controller 입니다.
 *
 * @author 박세완
 * @author 조현진
 * @version 1.0.0
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private static final String DEFAULT_PRODUCT_URI = "products";

    /**
     * 상품 검색을 위한 GET Mapping 을 지원합니다.
     * 카테고리 코드, 카테고리분류코드 코드를 동시에 받아 조건에 맞는 상품 리스트를 반환합니다.
     *
     * @param categoryCode - 2차 분류입니다. ex) 101 - 채소, 102 -  두부, 고구마
     * @return List &lt;ProductResponse&gt; 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}")
    public ResponseEntity<List<ElasticProduct>> findProductsByCategory(
            @PathVariable final String categoryCode, final Pageable pageable) {

        List<ElasticProduct> productResponseList =
                productService.findProductByCategory(pageable, categoryCode);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_PRODUCT_URI + "/categories/" + categoryCode))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productResponseList);
    }

    /**
     * 전체 목록에서 검색한 상품 목록을 반환합니다.
     *
     * @param keyword - 검색어입니다.
     * @param page    - 조회 할 페이지 정보입니다.
     * @return 전체 목록에서 검색한 상품 목록을 반환합니다.
     * @throws ParseException          파싱 도중 예외 처리입니다.
     * @throws JsonProcessingException Json 과 관련된 예외 처리입니다.
     * @since 1.0.0
     */
    @GetMapping("/search")
    public ResponseEntity<List<SearchProductResponse>> searchProductList(@RequestParam final String keyword,
                                                                         @RequestParam final Integer page) throws ParseException, JsonProcessingException {

        List<SearchProductResponse> productList =
                productService.searchProductList(keyword, page);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_PRODUCT_URI + "/search?keyword=" + keyword + "&page=" + page))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productList);
    }

    /**
     * 카테고리 목록에서 검색한 상품 목록을 반환합니다.
     *
     * @param categoryId - 지정한 카테고리 식별번호입니다.
     * @param keyword    - 검색어입니다.
     * @param page       - 조회 할 페이지 정보입니다.
     * @return 카테고리 목록에서 검색한 상품 목록을 반환합니다.
     * @throws ParseException          파싱 도중 예외 처리입니다.
     * @throws JsonProcessingException Json 과 관련된 예외 처리입니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryId}/search")
    public ResponseEntity<List<SearchProductResponse>> searchProductListByCategory(@PathVariable final String categoryId,
                                                                                   @RequestParam final String keyword,
                                                                                   @RequestParam final Integer page) throws ParseException, JsonProcessingException {

        List<SearchProductResponse> productList =
                productService.searchProductListByCategory(categoryId, keyword, page);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                     DEFAULT_PRODUCT_URI + "/categories/" + categoryId + "/search?keyword=" + keyword + "&page=" + page))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productList);
    }

    /**
     * 카테고리 목록내에서 선택한 가격 정렬 옵션으로 정렬된 상품 목록을 반환합니다.
     *
     * @param categoryId - 지정한 카테고리 식별번호입니다.
     * @param option     - 검색한 목록을 정렬할 가격옵션을 정렬 값입니다.
     * @param keyword    - 검색어입니다.
     * @param page       - 조회 할 페이지 정보입니다.
     * @return 카테고리 목록내에서 선택한 가격 정렬 옵션으로 정렬된 상품 목록을 반환합니다.
     * @throws ParseException          파싱 도중 예외 처리입니다.
     * @throws JsonProcessingException Json 과 관련된 예외 처리입니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryId}/price/{option}/search")
    public ResponseEntity<List<SearchProductResponse>> searchProductListByPrice(@PathVariable final String categoryId,
                                                                                @PathVariable final String option,
                                                                                @RequestParam final String keyword,
                                                                                @RequestParam final Integer page) throws ParseException, JsonProcessingException {

        List<SearchProductResponse> productList =
                productService.searchProductListByPrice(categoryId, option, keyword, page);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                     DEFAULT_PRODUCT_URI + "/categories/" + categoryId + "/price/" + option + "?keyword=" + keyword + "&page=" + page))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productList);
    }

}
