package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.productinquiry.ProductInquiryPostNotFoundException;
import com.nhnacademy.marketgg.server.repository.MemberRepository;
import com.nhnacademy.marketgg.server.repository.ProductRepository;
import com.nhnacademy.marketgg.server.repository.productInquiryPost.ProductInquiryPostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("상품 문의 등록 성공 테스트")
    void testCreateProductInquiry() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        productInquiryPostService.createProductInquiry(productInquiryRequest, 1L);

        verify(memberRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findById(anyLong());
        verify(productInquiryPostRepository, times(1)).save(any(ProductInquiryPost.class));
    }

    @Test
    @DisplayName("상품 문의 등록 실패 테스트")
    void testCreateProductInquiryFail() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> productInquiryPostService.createProductInquiry(productInquiryRequest, 1L))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("상품에 대해서 상품 문의 전체 조회 성공 테스트")
    void testRetrieveProductInquiryByProductId() {
        productInquiryPostService.retrieveProductInquiryByProductId(1L);

        verify(productInquiryPostRepository, times(1)).findALLByProductNo(anyLong());
    }

    @Test
    @DisplayName("특정 회원이 작성한 상품 문의 전체 조회 성공 테스트")
    void testRetrieveProductInquiryByMemberId() {
        productInquiryPostService.retrieveProductInquiryByMemberId(1L);

        verify(productInquiryPostRepository, times(1)).findAllByMemberNo(anyLong());
    }

    @Test
    @DisplayName("상품 문의에 대한 답글 등록 성공 테스트")
    void testUpdateProductInquiryReply() {
        given(productInquiryPostRepository.findById(new ProductInquiryPost.Pk(1L, 1L)))
                .willReturn(Optional.of(productInquiryPost));

        productInquiryPostService.updateProductInquiryReply(productInquiryRequest, 1L, 1L);

        verify(productInquiryPostRepository, times(1)).findById(any(ProductInquiryPost.Pk.class));
        verify(productInquiryPostRepository, times(1)).save(any(ProductInquiryPost.class));
    }

    @Test
    @DisplayName("상품 문의에 대한 답글 등록 실패 테스트")
    void testUpdateProductInquiryReplyFail() {
        given(productInquiryPostRepository.findById(new ProductInquiryPost.Pk(1L, 1L)))
                .willReturn(Optional.empty());
        assertThatThrownBy(() -> productInquiryPostService.updateProductInquiryReply(productInquiryRequest, 1L, 1L))
                .isInstanceOf(ProductInquiryPostNotFoundException.class);
    }

    @Test
    @DisplayName("상품 문의 삭제 성공 테스트")
    void testDeleteProductInquiry() {
        productInquiryPostService.deleteProductInquiry(1L, 1L);
        verify(productInquiryPostRepository, times(1)).deleteById(new ProductInquiryPost.Pk(1L, 1L));
    }

}
