package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;

import java.util.List;

/**
 * 상품 문의 서비스입니다.
 *
 * @version 1.0.0
 */
public interface ProductInquiryPostService {

    /**
     * 상품에 대한 상품 문의 글을 생성합니다.
     * 상품 문의 글에는 제목, 내용, 비밀 문의 글 여부가 있습니다.
     *
     * @param productInquiryRequest - 상품 문의 글을 생성하기 위한 DTO 입니다.
     * @param productId             - 상품 문의 글을 남길 상품의 PK 입니다.
     * @since 1.0.0
     */
    void createProductInquiry(ProductInquiryRequest productInquiryRequest, Long productId);

    /**
     * 상품에 대한 모든 상품 문의 글을 조회합니다.
     *
     * @param productId     - 조회할 상품의 PK 입니다.
     * @return              - 상품 문의 글을 List 로 반환합니다.
     */
    List<ProductInquiryResponse> retrieveProductInquiryByProductId(Long productId);

    /**
     * 회원이 남긴 모든 상품 문의 글을 조회합니다.
     *
     * @param memberId      - 조회할 회원의 PK 입니다.
     * @return              - 상품 문의 글을 List 로 반환합니다.
     */
    List<ProductInquiryResponse> retrieveProductInquiryByMemberId(Long memberId);

    /**
     * 상품 문의 글에 대한 관리자의 답글을 상품 문의 글에 업데이트합니다.
     *
     * @param inquiryReply      - 상품 문의 글에 대한 답글이 답긴 DTO 입니다.
     * @param inquiryId         - 상품 문의 글의 PK 입니다.
     * @param productId         - 상품의 PK 입니다.
     * @since 1.0.0
     */
    void updateProductInquiryReply(ProductInquiryRequest inquiryReply, Long inquiryId, Long productId);

    /**
     * 상품 문의 글을 삭제합니다.
     *
     * @param inquiryId     - 삭제할 상품 문의 글의 PK 입나다.
     * @param productId     - 상품의 PK 입니다.
     */
    void deleteProductInquiry(Long inquiryId, Long productId);
}
