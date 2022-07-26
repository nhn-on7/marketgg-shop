package com.nhnacademy.marketgg.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.dto.response.temp.PageListResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * 상품 서비스 입니다.
 *
 * @version 1.0.0
 */
public interface ProductService {

    /**
     * 상품을 생성합니다.
     *
     * @param productRequest - 상품 생성을 위한 DTO입니다. deletedAt 속성만 nullable 입니다.
     * @param image          - 상품 생성을 위한 MultipartFile 타입 파라미터입니다. null일 수 없습니다.
     * @throws IOException
     * @since 1.0.0
     */
    void createProduct(final ProductCreateRequest productRequest, MultipartFile image) throws IOException;

    /**
     * 모든 상품을 조회합니다.
     *
     * @return - 상품 리스트를 반환합니다.
     * @since 1.0.0
     */

    PageListResponse<ProductResponse> retrieveProducts(Pageable pageable);

    /**
     * 상품 Id를 통해 상세 정보를 조회합니다.
     *
     * @param productId - 상품의 PK값 입니다.
     * @return - PK에 해당하는 상품을 반환합니다.
     * @since 1.0.0
     */
    ProductResponse retrieveProductDetails(final Long productId);

    /**
     * 상품 id를 인자로 받아 해당 상품이 존재할 경우 수정합니다.
     * deletedAt을 제외한 모든 속성이 존재해야 합니다.
     *
     * @param productRequest - 상품 수정을 위한 DTO 입니다.
     * @param image          - 상품 수정을 위한 MultipartFile 타입 인자입니다.
     * @param productId      - 상품의 PK 값 입니다.
     * @throws IOException
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
     * keyword에 해당하는 값이 상품 이름에 존재하면 해당 상품을 반환합니다.
     *
     * @param keyword - String 타입입니다.
     * @return - 상품 리스트를 반환합니다.
     * @since 1.0.0
     */
    List<ProductResponse> searchProductsByName(final String keyword);

    /**
     * 카테고리로 상품 목록을 조회합니다.
     *
     * @param categoryCode - 카테고리 2차 분류입니다.
     * @return - 해당하는 카테고리의 상품 리스트를 반환합니다.
     */

    Page<ProductResponse> searchProductByCategory(final String categoryCode, final Pageable pageable);

}
