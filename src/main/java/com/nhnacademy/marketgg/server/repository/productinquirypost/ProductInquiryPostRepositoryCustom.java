package com.nhnacademy.marketgg.server.repository.productinquirypost;

import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryByProductResponse;
import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryByMemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 상품 문의 Repository interface 입니다.
 */
@NoRepositoryBean
public interface ProductInquiryPostRepositoryCustom {

    /**
     * 상품 번호로 전체 상품 문의를 조회합니다.
     *
     * @param id       - 조회할 상품의 id 입니다.
     * @param pageable - 요청하는 page 의 정보를 가지고 있습니다.
     * @return - 상품 문의 List 가 반환됩니다.
     */
    Page<ProductInquiryByProductResponse> findAllByProductNo(final Long id, Pageable pageable);

    /**
     * 회원 번호로 전체 상품 문의를 조회합니다.
     *
     * @param id       - 조회할 회원의 id 입니다.
     * @param pageable - 요청하는 page 의 정보를 가지고 있습니다.
     * @return - 상품 문의 List 가 반환됩니다.
     */
    Page<ProductInquiryByMemberResponse> findAllByMemberNo(final Long id, Pageable pageable);
}
