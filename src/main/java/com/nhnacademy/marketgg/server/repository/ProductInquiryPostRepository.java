package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductInquiryPostRepository extends JpaRepository<ProductInquiryPost, ProductInquiryPost.Pk> {

    @Query("SELECT pir FROM ProductInquiryPost pir WHERE pir.product.productNo = :id")
    List<ProductInquiryResponse> findALLByProductNo(Long id);

    @Query("SELECT pir from ProductInquiryPost pir WHERE pir.member.memberNo = :id")
    List<ProductInquiryResponse> findAllByMemberNo(Long id);

}
