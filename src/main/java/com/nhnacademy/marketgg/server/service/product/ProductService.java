package com.nhnacademy.marketgg.server.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.request.product.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.product.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.DefaultPageResult;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.dto.response.product.ProductResponse;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.dto.response.SearchProductResponse;
import java.io.IOException;
import java.util.List;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * 상품 서비스 입니다.
 *
 * @author 박세완
 * @author 조현진
 * @version 1.0.0
 */
public interface ProductService {

    /**
     * 상품을 생성합니다.
     *
     * @param productRequest - 상품 생성을 위한 DTO입니다. deletedAt 속성만 nullable 입니다.
     * @param image          - 상품 생성을 위한 MultipartFile 타입 파라미터입니다. null일 수 없습니다.
     * @throws IOException 입출력에 관한 예외처리입니다.
     * @since 1.0.0
     */
    void createProduct(final ProductCreateRequest productRequest, MultipartFile image) throws IOException;

    /**
     * 모든 상품을 조회합니다.
     *
     * @return - 상품 리스트를 반환합니다.
     * @since 1.0.0
     */

    DefaultPageResult<ProductResponse> retrieveProducts(Pageable pageable);

    /**
     * 상품 Id를 통해 상세 정보를 조회합니다.
     *
     * @param productId - 상품의 PK값 입니다.
     * @return - PK에 해당하는 상품을 반환합니다.
     * @since 1.0.0
     */
    SingleResponse<ProductResponse> retrieveProductDetails(final Long productId);

    /**
     * 상품 id를 인자로 받아 해당 상품이 존재할 경우 수정합니다.
     * deletedAt을 제외한 모든 속성이 존재해야 합니다.
     *
     * @param productRequest - 상품 수정을 위한 DTO 입니다.
     * @param image          - 상품 수정을 위한 MultipartFile 타입 인자입니다.
     * @param productId      - 상품의 PK 값 입니다.
     * @throws IOException 입출력에 관한 예외처리입니다.
     * @since 1.0.0
     */
    void updateProduct(final ProductUpdateRequest productRequest, MultipartFile image, final Long productId)
            throws IOException;

    /**
     * 상품 id를 인자로 받아 해당 상품이 존재할 경우 소프트 삭제합니다.
     * delete query 가 날아가지 않습니다.
     * 상품의 상태를 삭제 상태로 변경 합니다
     *
     * @param productId - 상품의 PK 값 입니다.
     * @since 1.0.0
     */
    void deleteProduct(final Long productId);

    void restoreProduct(final Long id);

    /**
     * 전체 목록에서 검색한 상품 목록을 반환합니다.
     *
     * @param searchRequest - 검색을 진행할 정보입니다.
     * @return 전체 목록에서 검색한 상품 목록을 반환합니다.
     * @throws ParseException          파싱 도중 예외 처리입니다.
     * @throws JsonProcessingException Json 과 관련된 예외 처리입니다.
     * @since 1.0.0
     */
    List<SearchProductResponse> searchProductList(final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException;

    /**
     * 카테고리 목록에서 검색한 상품 목록을 반환합니다.
     *
     * @param searchRequest - 검색을 진행할 정보입니다.
     * @return 카테고리 목록에서 검색한 상품 목록을 반환합니다.
     * @throws ParseException          파싱 도중 예외 처리입니다.
     * @throws JsonProcessingException Json 과 관련된 예외 처리입니다.
     * @since 1.0.0
     */
    List<SearchProductResponse> searchProductListByCategory(final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException;

    /**
     * 카테고리 목록내에서 선택한 가격 정렬 옵션으로 정렬된 상품 목록을 반환합니다.
     *
     * @param option        - 검색한 목록을 정렬할 가격옵션을 정렬 값입니다.
     * @param searchRequest - 검색을 진행할 정보입니다.
     * @return 카테고리 목록내에서 선택한 가격 정렬 옵션으로 정렬된 상품 목록을 반환합니다.
     * @throws ParseException          파싱 도중 예외 처리입니다.
     * @throws JsonProcessingException Json 과 관련된 예외 처리입니다.
     * @since 1.0.0
     */
    List<SearchProductResponse> searchProductListByPrice(final String option, final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException;

}
