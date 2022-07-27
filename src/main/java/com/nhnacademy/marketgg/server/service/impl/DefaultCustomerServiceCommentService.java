package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.response.CustomerServiceCommentDto;
import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.customerservicecomment.CustomerServiceCommentNotFoundException;
import com.nhnacademy.marketgg.server.exception.customerservicepost.CustomerServicePostNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.mapper.impl.CustomerServiceCommentMapper;
import com.nhnacademy.marketgg.server.repository.customerservicecomment.CustomerServiceCommentRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.CustomerServiceCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultCustomerServiceCommentService implements CustomerServiceCommentService {

    private final CustomerServiceCommentMapper customerServiceCommentMapper;
    private final CustomerServiceCommentRepository customerServiceCommentRepository;
    private final CustomerServicePostRepository customerServicePostRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void createComment(final Long inquiryId, final Long memberId, final CustomerServiceCommentDto csCommentDto) {
        CustomerServiceComment customerServiceComment = customerServiceCommentMapper.toEntity(csCommentDto);
        CustomerServicePost customerServicePost = customerServicePostRepository.findById(inquiryId).orElseThrow(
                CustomerServicePostNotFoundException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        customerServiceComment.setMember(member);
        customerServiceComment.setCustomerServicePost(customerServicePost);
        customerServiceComment.setCreatedAt(LocalDateTime.now());

        customerServiceCommentRepository.save(customerServiceComment);
    }

    @Override
    public CustomerServiceCommentDto retrieveComment(final Long commentId) {
        CustomerServiceComment comment = customerServiceCommentRepository.findById(commentId).orElseThrow(
                CustomerServiceCommentNotFoundException::new);

        return customerServiceCommentMapper.toDto(comment);
    }

    @Override
    public List<CustomerServiceCommentDto> retrieveCommentsByInquiry(final Long inquiryId) {
        List<CustomerServiceComment> comments = customerServiceCommentRepository.findByInquiry(inquiryId);

        return comments.stream()
                       .map(customerServiceCommentMapper::toDto)
                       .collect(Collectors.toUnmodifiableList());
    }

}
