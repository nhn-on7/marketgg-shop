package com.nhnacademy.marketgg.server.service.product;

import static com.nhnacademy.marketgg.server.repository.auth.AuthAdapter.checkResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoRequest;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoResponse;
import com.nhnacademy.marketgg.server.dto.request.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.ProductInquiryPost;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.exception.productinquiry.ProductInquiryPostNotFoundException;
import com.nhnacademy.marketgg.server.repository.auth.AuthRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.repository.productinquirypost.ProductInquiryPostRepository;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품 문의 서비스입니다.
 *
 * @author 민아영
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DefaultProductInquiryPostService implements ProductInquiryPostService {

    private final ProductInquiryPostRepository productInquiryPostRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final AuthRepository authRepository;

    /**
     * {@inheritDoc}
     *
     * @param memberInfo            상품 문의를 남기는 회원의 정보 입니다.
     * @param productInquiryRequest 상품 문의 글을 생성하기 위한 DTO 입니다.
     * @param id                    상품 문의 글을 남길 상품의 PK 입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Override
    @Transactional
    public void createProductInquiry(final MemberInfo memberInfo,
                                     @Valid final ProductInquiryRequest productInquiryRequest,
                                     final Long id) {

        Member member = memberRepository.findById(memberInfo.getId())
                                        .orElseThrow(ProductInquiryPostNotFoundException
                                                         .MemberWriteInquiryNotFoundException::new);

        Product product = productRepository.findById(id)
                                           .orElseThrow(ProductInquiryPostNotFoundException
                                                            .ProductAtInquiryNotFoundException::new);

        ProductInquiryPost inquiryPost = this.toEntity(product, member, productInquiryRequest);

        productInquiryPostRepository.save(inquiryPost);
    }

    /**
     * {@inheritDoc}
     *
     * @param id       조회할 상품의 PK 입니다.
     * @param pageable 요청하는 page 의 정보를 담고 있습니다.
     * @return 상품 문의 List 와 페이지 정보를 PageEntity 에 담아 반환합니다.
     * @throws JsonProcessingException Json 과 관련된 예외 처리입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Override
    public PageEntity<ProductInquiryResponse> retrieveProductInquiryByProductId(final Long id, final Pageable pageable)
        throws JsonProcessingException {

        Page<ProductInquiryResponse> pageByProductNo = productInquiryPostRepository.findAllByProductNo(id, pageable);
        List<ProductInquiryResponse> productInquiryResponses = pageByProductNo.getContent();

        for (ProductInquiryResponse inquiry : productInquiryResponses) {
            MemberInfoRequest request = new MemberInfoRequest(inquiry.getUuid());
            MemberInfoResponse nameByUuid = checkResult(authRepository.getMemberInfo(request));
            inquiry.memberName(nameByUuid.getName());
        }

        return new PageEntity<>(pageByProductNo.getNumber(), pageByProductNo.getSize(),
                                pageByProductNo.getTotalPages(), productInquiryResponses);
    }

    /**
     * {@inheritDoc}
     *
     * @param memberInfo 조회할 회원의 정보입니다.
     * @param pageable   요청하는 page 의 정보를 담고 있습니다.
     * @return 상품 문의 List 와 페이지 정보를 PageEntity 에 담아 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Override
    public PageEntity<ProductInquiryResponse> retrieveProductInquiryByMemberId(final MemberInfo memberInfo,
                                                                           final Pageable pageable) {

        Page<ProductInquiryResponse> allByMemberNo = productInquiryPostRepository.findAllByMemberNo(memberInfo.getId(),
                                                                                                pageable);
        return new PageEntity<>(allByMemberNo.getNumber(), allByMemberNo.getSize(),
                                allByMemberNo.getTotalPages(), allByMemberNo.getContent());
    }

    /**
     * {@inheritDoc}
     *
     * @param inquiryReply 상품 문의 글에 대한 답글이 답긴 DTO 입니다.
     * @param inquiryId    상품 문의 글의 PK 입니다.
     * @param productId    상품의 PK 입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Override
    @Transactional
    public void updateProductInquiryReply(final String inquiryReply,
                                          final Long inquiryId,
                                          final Long productId) {
        ProductInquiryPost inquiryPost =
            productInquiryPostRepository.findById(inquiryId)
                                        .orElseThrow(ProductInquiryPostNotFoundException::new);

        inquiryPost.updateInquiry(inquiryReply);
        productInquiryPostRepository.save(inquiryPost);
    }

    /**
     * {@inheritDoc}
     *
     * @param inquiryId - 삭제할 상품 문의 글의 PK 입나다.
     * @author 민아영
     * @since 1.0.0
     */
    @Override
    @Transactional
    public void deleteProductInquiry(final Long inquiryId, final Long productId) {
        if (productRepository.findById(productId).isEmpty()) {
          throw new ProductNotFoundException();
        }
        productInquiryPostRepository.deleteById(inquiryId);
    }

}
