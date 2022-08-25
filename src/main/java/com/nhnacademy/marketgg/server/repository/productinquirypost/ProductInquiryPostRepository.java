package com.nhnacademy.marketgg.server.repository.productinquirypost;

import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductInquiryPostRepository extends
    JpaRepository<ProductInquiryPost, Long>, ProductInquiryPostRepositoryCustom {

}
