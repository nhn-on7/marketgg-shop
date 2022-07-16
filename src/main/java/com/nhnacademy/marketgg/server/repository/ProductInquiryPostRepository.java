package com.nhnacademy.marketgg.server.repository.productInquiryPost;

import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductInquiryPostRepository extends JpaRepository<ProductInquiryPost, ProductInquiryPost.Pk>, ProductInquiryPostRepositoryCustom {

}
