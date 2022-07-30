package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.CommentResponse;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.server.elastic.document.ElasticBoard;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticBoardRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultCustomerServicePostService implements CustomerServicePostService {

    private final CustomerServicePostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final CustomerServiceCommentRepository commentRepository;
    private final ElasticBoardRepository elasticBoardRepository;

    private static final String OTO_INQUIRY = "1:1문의";

    @Transactional
    @Override
    public void createOtoInquiry(final Long memberId, final PostRequest postRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY);
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        CustomerServicePost csPost = new CustomerServicePost(member, category, postRequest);

        postRepository.save(csPost);
        elasticBoardRepository.save(new ElasticBoard(customerServicePost));
    }

    @Override
    public PostResponseForOtoInquiry retrieveCustomerServicePost(final Long inquiryId) {
        PostResponseForOtoInquiry otoInquiry = postRepository.findOtoInquiryById(inquiryId);

        return addCommentList(otoInquiry);
    }

    @Override
    public List<PostResponseForOtoInquiry> retrieveOtoInquiries(final Pageable pageable) {
        List<PostResponseForOtoInquiry> result = new ArrayList<>();
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY);
        List<PostResponseForOtoInquiry> otoInquiries = postRepository.findPostsByCategoryId(pageable,
                                                                                            categoryId)
                                                                     .getContent();

        for (PostResponseForOtoInquiry otoInquiry : otoInquiries) {
            result.add(addCommentList(otoInquiry));
        }

        return result;
    }

    @Override
    public List<PostResponseForOtoInquiry> retrieveOwnOtoInquiries(final Pageable pageable, final Long memberId) {
        List<PostResponseForOtoInquiry> result = new ArrayList<>();
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY);

        List<PostResponseForOtoInquiry> ownOtoInquiries = postRepository.findPostByCategoryAndMember(
                                                                                               pageable,
                                                                                               categoryId,
                                                                                               memberId)
                                                                        .getContent();

        for (PostResponseForOtoInquiry otoInquiry : ownOtoInquiries) {
            result.add(addCommentList(otoInquiry));
        }

        return result;
    }

    private PostResponseForOtoInquiry addCommentList(PostResponseForOtoInquiry otoInquiry) {
        List<CommentResponse> commentList = commentRepository.findByInquiryId(otoInquiry.getId());

        return PostResponseForOtoInquiry.builder().otoInquiry(otoInquiry).commentList(commentList).build();
    }

    @Override
    public void updateInquiryStatus(Long inquiryId, PostStatusUpdateRequest status) {
        CustomerServicePost inquiry = postRepository.findById(inquiryId)
                                                    .orElseThrow(
                                                                           CustomerServicePostNotFoundException::new);

        inquiry.updatePostStatus(status.getStatus());
        postRepository.save(inquiry);
    }

    @Transactional
    @Override
    public void deleteCustomerServicePost(final Long csPostId) {
        CustomerServicePost otoInquiry = postRepository.findById(csPostId)
                                                       .orElseThrow(
                                                                              CustomerServicePostNotFoundException::new);
        List<Long> commentIds = commentRepository.findByInquiryId(csPostId)
                                                 .stream()
                                                 .map(CommentResponse::getId)
                                                 .collect(Collectors.toList());

        commentRepository.deleteAllById(commentIds);
        postRepository.delete(otoInquiry);
        elasticBoardRepository.deleteById(csPostId);
    }

}
