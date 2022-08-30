package com.nhnacademy.marketgg.server.controller.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.DefaultPageRequest;
import com.nhnacademy.marketgg.server.dto.response.product.ProductDetailResponse;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.dto.response.product.ProductListResponse;
import com.nhnacademy.marketgg.server.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * 전체 목록에서 검색한 상품 목록을 반환합니다.
     *
     * @return 전체 목록에서 검색한 상품 목록을 반환합니다.
     * @throws ParseException          파싱 도중 예외 처리입니다.
     * @throws JsonProcessingException Json 과 관련된 예외 처리입니다.
     * @since 1.0.0
     */
    @Operation(summary = "전체 상품 검색",
               description = "전체 목록에서 상품의 검색을 진행합니다.",
               parameters = @Parameter(name = "searchRequest", description = "검색 정보", required = true),
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @PostMapping("/search")
    public ResponseEntity<PageEntity<List<ProductListResponse>>> searchProductList(
            @Valid @RequestBody final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException {

        PageEntity<List<ProductListResponse>> productList = productService.searchProductList(searchRequest);

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
    @Operation(summary = "카테고리 목록 내 상품 검색",
               description = "지정한 카테고리 목록 내에서 검색한 상품 목록을 검색합니다.",
               parameters = { @Parameter(name = "categoryId", description = "카테고리 식별번호", required = true),
                       @Parameter(name = "searchRequest", description = "검색 정보", required = true) },
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @PostMapping("/categories/{categoryId}/search")
    public ResponseEntity<PageEntity<List<ProductListResponse>>> searchProductListByCategory(
            @PathVariable @NotBlank @Size(min = 1, max = 6) final String categoryId,
            @Valid @RequestBody final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException {

        PageEntity<List<ProductListResponse>> productList =
                productService.searchProductListByCategory(searchRequest);


        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                 DEFAULT_PRODUCT_URI + "/categories/" + categoryId + "/search"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productList);
    }

    /**
     * 선택한 가격 정렬 옵션으로 정렬된 상품 목록을 반환합니다.
     *
     * @param option        - 검색한 목록을 정렬할 가격옵션을 정렬 값입니다.
     * @param searchRequest - 검색을 진행할 정보입니다.
     * @return 카테고리 목록내에서 선택한 가격 정렬 옵션으로 정렬된 상품 목록을 반환합니다.
     * @throws ParseException          파싱 도중 예외 처리입니다.
     * @throws JsonProcessingException Json 과 관련된 예외 처리입니다.
     * @since 1.0.0
     */
    @Operation(summary = "옵션에 따른 상품 목록조회",
               description = "지정한 가격 옵션에따른 상품 목록을 검색합니다.",
               parameters = { @Parameter(name = "categoryId", description = "카테고리 식별번호", required = true),
                       @Parameter(name = "option", description = "지정한 옵션의 값", required = true),
                       @Parameter(name = "searchRequest", description = "검색 정보", required = true) },
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @PostMapping("/sort-price/{option}/search")
    public ResponseEntity<PageEntity<List<ProductListResponse>>> searchProductListByPrice(
            @PathVariable @NotBlank @Min(1) final String option,
            @Valid @RequestBody final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException {

        PageEntity<List<ProductListResponse>> productList =
                productService.searchProductListByPrice(option, searchRequest);


        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                     DEFAULT_PRODUCT_URI + "/sort-price/" + option))
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
    @Operation(summary = "옵션에 따른 상품 목록조회",
               description = "지정한 카테고리 내에서 지정한 가격 옵션에따른 상품 목록을 검색합니다.",
               parameters = { @Parameter(name = "categoryId", description = "카테고리 식별번호", required = true),
                       @Parameter(name = "option", description = "지정한 옵션의 값", required = true),
                       @Parameter(name = "searchRequest", description = "검색 정보", required = true) },
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @PostMapping("/categories/{categoryId}/sort-price/{option}/search")
    public ResponseEntity<PageEntity<List<ProductListResponse>>> searchProductListByPriceFromCategory(
            @PathVariable @NotBlank @Size(min = 1, max = 6) final String categoryId,
            @PathVariable @NotBlank @Min(1) final String option,
            @Valid @RequestBody final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException {

        PageEntity<List<ProductListResponse>> productList =
                productService.searchProductListByPrice(option, searchRequest);


        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                 DEFAULT_PRODUCT_URI + "/categories/" + categoryId + "/sort-price/" + option))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productList);
    }

    /**
     * 전체 상품 목록 조회를 위한 GET Mapping 을 지원합니다.
     *
     * @return - List&lt;ProductDetailResponse&gt; 를 담은 응답 객체를 반환 합니다.
     * @since 1.0.0
     */

    @Operation(summary = "상품목록 전체 조회",
               description = "마켓 GG에 입장하는 순간 보여야하는 상품들의 목록입니다.",
               parameters = @Parameter(name = "page", description = "현재 페이지. 기본값은 0", required = true),
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))

    @GetMapping
    public ResponseEntity<PageEntity<ProductListResponse>> retrieveProducts(
        @RequestParam(value = "page", defaultValue = "0") final Integer page) {

        DefaultPageRequest pageRequest = new DefaultPageRequest(page);

        Page<ProductListResponse> productListResponses =
            this.productService.retrieveProducts(pageRequest.getPageable());

        PageEntity<ProductListResponse> pageEntity = new PageEntity<>(productListResponses.getNumber(),
                                                                        productListResponses.getSize(),
                                                                        productListResponses.getTotalPages(),
                                                                        productListResponses.getContent());

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_PRODUCT_URI))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(pageEntity);
    }

    /**
     * 카테고리로 상품 조회를 하기 위한 컨트롤러입니다.
     */

    @GetMapping("/categories/{categoryCode}")
    public ResponseEntity<PageEntity<ProductListResponse>> retrieveProductsByCategory(@PathVariable final String categoryCode,
        @RequestParam(value = "page", defaultValue = "0") final Integer page) {

        DefaultPageRequest pageRequest = new DefaultPageRequest(page);

        Page<ProductListResponse> productListResponses =
            this.productService.retrieveProductsByCategory(categoryCode, pageRequest.getPageable());

        PageEntity<ProductListResponse> pageEntity = new PageEntity<>(productListResponses.getNumber(),
                                                                      productListResponses.getSize(),
                                                                      productListResponses.getTotalPages(),
                                                                      productListResponses.getContent());

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_PRODUCT_URI))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(pageEntity);
    }

    /**
     * 상품 상세 정보 조회를 위한 GET Mapping 을 지원합니다.
     *
     * @param productId - 상품의 PK로 조회합니다.
     * @return - ProductDetailResponse 를 담은 응답 객체를 반환 합니다.
     * @since 1.0.0
     */

    @Operation(summary = "상품 상세 조회",
               description = "상품을 클릭했을 때 보이는 상품의 상세정보입니다.",
               parameters = @Parameter(name = "productId", description = "조회하려는 상품의 상품 번호", required = true),
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @GetMapping("/{productId}")
    public ResponseEntity<ShopResult<ProductDetailResponse>> retrieveProductDetails(
        @PathVariable final Long productId, final MemberInfo memberInfo) {

        ProductDetailResponse productDetailResponse = this.productService.retrieveProductDetails(productId, memberInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_PRODUCT_URI))
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(ShopResult.successWith(productDetailResponse));
    }

}
