package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.CommentResponse;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.category.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.exception.customerservicepost.CustomerServicePostNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.customerservicecomment.CustomerServiceCommentRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultCustomerServicePostService implements CustomerServicePostService {

    private final CustomerServicePostRepository customerServicePostRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final CustomerServiceCommentRepository customerServiceCommentRepository;

    private static final String OTO_INQUIRY = "1:1문의";

    @Transactional
    @Override
    public void createOtoInquiry(final Long memberId, final PostRequest postRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY);
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        CustomerServicePost csPost = new CustomerServicePost(member, category, postRequest);

        customerServicePostRepository.save(csPost);
    }

    @Override
    public PostResponseForOtoInquiry retrieveCustomerServicePost(final Long inquiryId) {
        PostResponseForOtoInquiry otoInquiry = customerServicePostRepository.findOtoInquiryById(inquiryId);
        List<CommentResponse> commentList = customerServiceCommentRepository.findByInquiryId(inquiryId);
        PostResponseForOtoInquiry result = PostResponseForOtoInquiry.builder()
                                                                    .otoInquiry(otoInquiry)
                                                                    .commentList(commentList)
                                                                    .build();

        return result;
    }

    @Override
    public List<PostResponseForOtoInquiry> retrieveOtoInquiries(final Pageable pageable) {
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY);
        List<PostResponseForOtoInquiry> otoInquiries = customerServicePostRepository.findPostsByCategoryId(pageable,
                                                                                                           categoryId)
                                                                                    .getContent();

        return otoInquiries;
    }

    @Override
    public List<PostResponseForOtoInquiry> retrieveOwnOtoInquiries(final Pageable pageable, final Long memberId) {
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY);

        List<PostResponseForOtoInquiry> ownOtoInquiries = customerServicePostRepository.findPostByCategoryAndMember(pageable,
                                                                                                              categoryId,
                                                                                                              memberId)
                                                                                 .getContent();

        return ownOtoInquiries;
    }

    @Override
    public void updateInquiryStatus(Long inquiryId, PostStatusUpdateRequest status) {
        CustomerServicePost inquiry = customerServicePostRepository.findById(inquiryId)
                                                                   .orElseThrow(
                                                                           CustomerServicePostNotFoundException::new);

        inquiry.updatePostStatus(status.getStatus());
        customerServicePostRepository.save(inquiry);
    }

    @Transactional
    @Override
    public void deleteCustomerServicePost(final Long csPostId) {
        CustomerServicePost otoInquiry = customerServicePostRepository.findById(csPostId)
                                                                      .orElseThrow(
                                                                              CustomerServicePostNotFoundException::new);
        List<Long> commentIds = customerServiceCommentRepository.findByInquiryId(csPostId)
                                                                .stream()
                                                                .map(CommentResponse::getId)
                                                                .collect(Collectors.toList());

        customerServiceCommentRepository.deleteAllById(commentIds);
        customerServicePostRepository.delete(otoInquiry);
    }

}
