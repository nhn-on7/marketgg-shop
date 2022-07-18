package com.nhnacademy.marketgg.server.repository.productInquiryPost;

import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInquiryPostRepository extends JpaRepository<ProductInquiryPost, ProductInquiryPost.Pk>, ProductInquiryPostRepositoryCustom {

}
