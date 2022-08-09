package com.nhnacademy.marketgg.server.controller.product;

import com.nhnacademy.marketgg.server.elastic.document.ElasticProduct;
import com.nhnacademy.marketgg.server.service.product.ProductService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 상품 Controller 입니다.
 *
 * @author 박세완, 조현진
 * @version 1.0.0
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private static final String DEFAULT_PRODUCT_URI = "products";

    /**
     * 상품 검색을 위한 GET Mapping을 지원합니다.
     * 카테고리 코드, 카테고리분류코드 코드를 동시에 받아 조건에 맞는 상품 리스트를 반환합니다.
     *
     * @param categoryCode - 2차 분류입니다. ex) 101 - 채소, 102 -  두부, 고구마
     * @return List &lt;ProductResponse&gt; 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}")
    public ResponseEntity<List<ElasticProduct>> searchProductsByCategory(
        @PathVariable final String categoryCode, final Pageable pageable) {

        List<ElasticProduct> productResponseList =
            productService.searchProductByCategory(pageable, categoryCode);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_PRODUCT_URI + "/search/" + categoryCode))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productResponseList);
    }

}
