package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryByMemberResponse;
import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import com.nhnacademy.marketgg.server.exception.productinquiry.ProductInquiryPostNotFoundException;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.repository.productinquirypost.ProductInquiryPostRepository;
import com.nhnacademy.marketgg.server.service.product.DefaultProductInquiryPostService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultProductInquiryPostServiceTest {

    @InjectMocks
    DefaultProductInquiryPostService productInquiryPostService;

    @Mock
    private ProductInquiryPostRepository productInquiryPostRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private Product product;
    @Mock
    private Member member;
    @Mock
    private ProductInquiryPost productInquiryPost;
    @Mock
    private ProductInquiryRequest productInquiryRequest;
    @Mock
    private MemberInfo memberInfo;

    Pageable pageable = PageRequest.of(0, 20);
    Page<ProductInquiryByMemberResponse> inquiryPosts = new PageImpl<>(List.of(), pageable, 0);
    Page<ProductInquiryResponse> inquiryPosts1 = new PageImpl<>(List.of(), pageable, 0);

    @Test
    @DisplayName("상품 문의 등록 성공 테스트")
    void testCreateProductInquiry() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        productInquiryPostService.createProductInquiry(memberInfo, productInquiryRequest, 1L);

        then(memberRepository).should(times(1)).findById(anyLong());
        then(productRepository).should(times(1)).findById(anyLong());
        then(productInquiryPostRepository).should(times(1)).save(any(ProductInquiryPost.class));
    }

    @Test
    @DisplayName("상품 문의 등록 실패 테스트")
    void testCreateProductInquiryFail() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> productInquiryPostService
                .createProductInquiry(memberInfo, productInquiryRequest, 2L))
                .isInstanceOf(ProductInquiryPostNotFoundException.MemberWriteInquiryNotFoundException.class);
    }

    @Test
    @DisplayName("상품에 대해서 상품 문의 전체 조회 성공 테스트")
    void testRetrieveProductInquiryByProductId() throws JsonProcessingException {
        given(productInquiryPostRepository.findAllByProductNo(anyLong(), any(PageRequest.class)))
                .willReturn(inquiryPosts1);
        productInquiryPostService.retrieveProductInquiryByProductId(1L, pageable);

        then(productInquiryPostRepository).should(times(1))
                                          .findAllByProductNo(anyLong(), any(PageRequest.class));
    }

    @Test
    @DisplayName("특정 회원이 작성한 상품 문의 전체 조회 성공 테스트")
    void testRetrieveProductInquiryByMemberId() {
        given(productInquiryPostRepository.findAllByMemberNo(anyLong(), any(PageRequest.class)))
                .willReturn(inquiryPosts);
        productInquiryPostService.retrieveProductInquiryByMemberId(memberInfo, pageable);

        then(productInquiryPostRepository).should(times(1))
                                          .findAllByMemberNo(anyLong(), any(PageRequest.class));
    }

    @Test
    @DisplayName("상품 문의에 대한 답글 등록 성공 테스트")
    void testUpdateProductInquiryReply() {
        given(productInquiryPostRepository.findById(new ProductInquiryPost.Pk(1L, 1L)))
                .willReturn(Optional.of(productInquiryPost));

        productInquiryPostService.updateProductInquiryReply(anyString(), 1L, 1L);

        then(productInquiryPostRepository).should(times(1))
                                          .findById(any(ProductInquiryPost.Pk.class));
        then(productInquiryPostRepository).should(times(1))
                                          .save(any(ProductInquiryPost.class));
    }

    @Test
    @DisplayName("상품 문의에 대한 답글 등록 실패 테스트")
    void testUpdateProductInquiryReplyFail() {
        given(productInquiryPostRepository.findById(new ProductInquiryPost.Pk(1L, 1L)))
                .willReturn(Optional.empty());
        assertThatThrownBy(
                () -> productInquiryPostService.updateProductInquiryReply(
                        anyString(), 1L, 1L))
                .isInstanceOf(ProductInquiryPostNotFoundException.class);
    }

    @Test
    @DisplayName("상품 문의 삭제 성공 테스트")
    void testDeleteProductInquiry() {
        productInquiryPostService.deleteProductInquiry(1L, 1L);
        then(productInquiryPostRepository).should(times(1))
                                          .deleteById(new ProductInquiryPost.Pk(1L, 1L));
    }

}
