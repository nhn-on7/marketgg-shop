package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CommentRequest;
import com.nhnacademy.marketgg.server.dto.response.CommentResponse;
import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.customerservicepost.CustomerServicePostNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.customerservicecomment.CustomerServiceCommentRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.CustomerServiceCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCustomerServiceCommentService implements CustomerServiceCommentService {

    private final CustomerServiceCommentRepository customerServiceCommentRepository;
    private final CustomerServicePostRepository customerServicePostRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void createComment(final Long inquiryId, final Long memberId, final CommentRequest commentRequest) {
        CustomerServicePost csPost = customerServicePostRepository.findById(inquiryId).orElseThrow(
                CustomerServicePostNotFoundException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        CustomerServiceComment comment = new CustomerServiceComment(csPost, member, commentRequest);

        customerServiceCommentRepository.save(comment);
    }

    @Override
    public CommentResponse retrieveComment(final Long commentId) {
        CommentResponse comment = customerServiceCommentRepository.findCommentById(commentId);

        return comment;
    }

    @Override
    public List<CommentResponse> retrieveCommentsByInquiry(final Long inquiryId) {
        List<CommentResponse> comments = customerServiceCommentRepository.findByInquiryId(inquiryId);

        return comments;
    }

}
