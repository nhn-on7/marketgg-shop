package com.nhnacademy.marketgg.server.repository.productinquirypost;

import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 상품 문의 Repository interface 입니다.
 *
 * @author 민아영
 * @version 1.0.0
 * @since 1.0.0
 */
@NoRepositoryBean
public interface ProductInquiryPostRepositoryCustom {

    /**
     * 상품 번호로 전체 상품 문의를 조회합니다.
     *
     * @param id       - 조회할 상품의 id 입니다.
     * @param pageable - 요청하는 page 의 정보를 가지고 있습니다.
     * @return - 상품 문의 List 가 반환됩니다.
     * @author 민아영
     * @since 1.0.0
     */
    Page<ProductInquiryResponse> findAllByProductNo(final Long id, Pageable pageable);

    /**
     * 회원 번호로 전체 상품 문의를 조회합니다.
     *
     * @param id       조회할 회원의 id 입니다.
     * @param pageable 요청하는 page 의 정보를 가지고 있습니다.
     * @return 상품 문의 List 가 반환됩니다.
     * @author 민아영
     * @since 1.0.0
     */
    Page<ProductInquiryResponse> findAllByMemberNo(final Long id, Pageable pageable);

    /**
     * 전체 상품 문의를 날짜의 내림차순 기준으로 정렬하여 조회합니다.
     *
     * @param pageable 요청하는 page 의 정보를 가지고 있습니다.
     * @return 상품 문의 List 가 반환됩니다.
     * @author 민아영
     * @since 1.0.0
     */
    Page<ProductInquiryResponse> findAllByAdmin(final Pageable pageable);

}
