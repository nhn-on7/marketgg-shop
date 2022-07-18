package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.exception.productinquiry.ProductInquiryPostNotFoundException;
import com.nhnacademy.marketgg.server.repository.MemberRepository;
import com.nhnacademy.marketgg.server.repository.productInquiryPost.ProductInquiryPostRepository;
import com.nhnacademy.marketgg.server.repository.ProductRepository;
import com.nhnacademy.marketgg.server.service.ProductInquiryPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultProductInquiryPostService implements ProductInquiryPostService {

    private final ProductInquiryPostRepository productInquiryPostRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createProductInquiry(final ProductInquiryRequest productInquiryRequest, final Long id) {
        Member member = memberRepository.findById(productInquiryRequest.getMemberId())
                                        .orElseThrow(MemberNotFoundException::new);

        Product product = productRepository.findById(id)
                                           .orElseThrow(ProductNotFoundException::new);

        ProductInquiryPost inquiryPost = new ProductInquiryPost(product, member, productInquiryRequest);

        productInquiryPostRepository.save(inquiryPost);
    }

    @Override
    public List<ProductInquiryResponse> retrieveProductInquiryByProductId(final Long id) {
        return productInquiryPostRepository.findALLByProductNo(id);
    }

    @Override
    public List<ProductInquiryResponse> retrieveProductInquiryByMemberId(final Long id) {
        return productInquiryPostRepository.findAllByMemberNo(id);
    }

    @Override
    @Transactional
    public void updateProductInquiryReply(final ProductInquiryRequest inquiryReply,
                                          final Long inquiryId,
                                          final Long productId) {
        ProductInquiryPost inquiryPost = productInquiryPostRepository.findById(new ProductInquiryPost.Pk(inquiryId, productId))
                                                                     .orElseThrow(ProductInquiryPostNotFoundException::new);

        inquiryPost.updateInquiry(inquiryReply);
        productInquiryPostRepository.save(inquiryPost);
    }

    @Override
    @Transactional
    public void deleteProductInquiry(final Long inquiryId, final Long productId) {
        productInquiryPostRepository.deleteById(new ProductInquiryPost.Pk(inquiryId, productId));
    }

}
