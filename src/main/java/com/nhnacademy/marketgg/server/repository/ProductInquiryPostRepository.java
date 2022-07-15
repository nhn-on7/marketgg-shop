package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInquiryPostRepository extends JpaRepository<ProductInquiryPost, ProductInquiryPost.Pk> {

    List<ProductInquiryResponse> findByProductProductNo(Long productId);

    List<ProductInquiryResponse> findByMember_MemberNo(Long memberId);

}
