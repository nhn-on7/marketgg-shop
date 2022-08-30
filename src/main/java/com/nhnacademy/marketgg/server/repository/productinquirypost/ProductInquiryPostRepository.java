package com.nhnacademy.marketgg.server.repository.productinquirypost;

import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductInquiryPostRepository extends
    JpaRepository<ProductInquiryPost, Long>, ProductInquiryPostRepositoryCustom {

}
