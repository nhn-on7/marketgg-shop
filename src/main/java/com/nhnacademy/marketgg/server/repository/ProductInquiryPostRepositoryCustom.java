package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ProductInquiryPostRepositoryCustom {
    List<ProductInquiryResponse> findALLByProductNo(final Long id);

    List<ProductInquiryResponse> findAllByMemberNo(final Long id);
}
