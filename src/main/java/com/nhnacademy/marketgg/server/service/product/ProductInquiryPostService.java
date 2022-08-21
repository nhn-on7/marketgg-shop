package com.nhnacademy.marketgg.server.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * 상품 문의 서비스입니다.
 *
 * @author 민아영
 * @version 1.0.0
 */
public interface ProductInquiryPostService {

    /**
     * 상품에 대한 상품 문의 글을 생성합니다.
     * 상품 문의 글에는 제목, 내용, 비밀 문의 글 여부가 있습니다.
     *
     * @param productInquiryRequest - 상품 문의 글을 생성하기 위한 DTO 입니다.
     * @param id                    - 상품 문의 글을 남길 상품의 PK 입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void createProductInquiry(MemberInfo memberInfo, ProductInquiryRequest productInquiryRequest, Long id);

    /**
     * 상품에 대한 모든 상품 문의 글을 조회합니다.
     *
     * @param id - 조회할 상품의 PK 입니다.
     * @return - 상품 문의 글을 List 로 반환합니다.
     * @author 민아영
     */
    PageEntity<ProductInquiryResponse> retrieveProductInquiryByProductId(Long id, Pageable pageable) throws JsonProcessingException;

    /**
     * 회원이 남긴 모든 상품 문의 글을 조회합니다.
     *
     * @param memberInfo - 조회할 회원의 정보입니다.
     * @return - 상품 문의 글을 List 로 반환합니다.
     * @author 민아영
     */
    PageEntity<ProductInquiryPost> retrieveProductInquiryByMemberId(MemberInfo memberInfo, Pageable pageable);

    /**
     * 상품 문의 글에 대한 관리자의 답글을 상품 문의 글에 업데이트합니다.
     *
     * @param inquiryReply - 상품 문의 글에 대한 답글이 답긴 DTO 입니다.
     * @param inquiryId    - 상품 문의 글의 PK 입니다.
     * @param productId    - 상품의 PK 입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void updateProductInquiryReply(String inquiryReply, Long inquiryId, Long productId);

    /**
     * 상품 문의 글을 삭제합니다.
     *
     * @param inquiryId - 삭제할 상품 문의 글의 PK 입나다.
     * @param productId - 상품의 PK 입니다.
     * @author 민아영
     */
    void deleteProductInquiry(Long inquiryId, Long productId);

    /**
     * 상품, 회원, 상품 문의 요청 데이터를 가지고 상품에 대한 문의 Entity 로 변환합니다.
     *
     * @param product - 상품 문의를 남길 상품 Entity 입니다.
     * @param member - 상품 문의를 남길 회원 Entity 입니다.
     * @param productInquiryRequest - 상품 문의 요청 데이터를 가진 Dto 입니다.
     * @return - 생성한 상품 문의 Entity 를 반환합니다.
     */
    default ProductInquiryPost toEntity(final Product product,
                                        final Member member,
                                        final ProductInquiryRequest productInquiryRequest) {

        return ProductInquiryPost.builder()
                                 .pk(new ProductInquiryPost.Pk(product.getId()))
                                 .product(product)
                                 .member(member)
                                 .title(productInquiryRequest.getTitle())
                                 .content(productInquiryRequest.getContent())
                                 .isSecret(productInquiryRequest.getIsSecret())
                                 .createdDate(LocalDateTime.now())
                                 .build();
    }

}
