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

@Service
@RequiredArgsConstructor
public class DefaultProductInquiryPostService implements ProductInquiryPostService {

    private final ProductInquiryPostRepository productInquiryPostRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final AuthRepository authRepository;


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

    @Override
    public PageEntity<ProductInquiryPost> retrieveProductInquiryByMemberId(final MemberInfo memberInfo,
                                                                           final Pageable pageable) {

        Page<ProductInquiryPost> allByMemberNo = productInquiryPostRepository.findAllByMemberNo(memberInfo.getId(),
                                                                                                pageable);
        return new PageEntity<>(allByMemberNo.getNumber(), allByMemberNo.getSize(),
                                allByMemberNo.getTotalPages(), allByMemberNo.getContent());
    }

    @Override
    @Transactional
    public void updateProductInquiryReply(final String inquiryReply,
                                          final Long inquiryId,
                                          final Long productId) {
        ProductInquiryPost inquiryPost =
            productInquiryPostRepository.findById(new ProductInquiryPost.Pk(inquiryId, productId))
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
