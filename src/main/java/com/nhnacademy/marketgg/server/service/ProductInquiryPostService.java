package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;

import java.util.List;

public interface ProductInquiryPostService {

    void createProductInquiry(ProductInquiryRequest productInquiryRequest, Long productId);

    List<ProductInquiryResponse> retrieveProductInquiryByProductId(Long productId);

    List<ProductInquiryResponse> retrieveProductInquiryByMemberId(Long memberId);

    void updateProductInquiryReply(Long inquiryId, Long productId);

    void deleteProductInquiry(Long inquiryId, Long productId);
}
