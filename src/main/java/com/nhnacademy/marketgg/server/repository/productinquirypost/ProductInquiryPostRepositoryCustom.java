package com.nhnacademy.marketgg.server.repository.productinquirypost;

import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ProductInquiryPostRepositoryCustom {
    Page<ProductInquiryResponse> findALLByProductNo(final Long id, Pageable pageable);

    Page<ProductInquiryResponse> findAllByMemberNo(final String uuid, Pageable pageable);
}
