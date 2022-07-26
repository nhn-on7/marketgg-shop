package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.response.CustomerServiceCommentDto;
import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
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

@Service
@RequiredArgsConstructor
public class DefaultCustomerServiceCommentService implements CustomerServiceCommentService {

    private final CustomerServiceCommentMapper customerServiceCommentMapper;
    private final CustomerServiceCommentRepository customerServiceCommentRepository;
    private final CustomerServicePostRepository customerServicePostRepository;
    private final MemberRepository memberRepository;

    private static final Long ADMIN_NUMBER = 1L;

    @Transactional
    @Override
    public void createComment(Long inquiryId, CustomerServiceCommentDto customerServiceCommentDto) {
        CustomerServiceComment customerServiceComment = customerServiceCommentMapper.toEntity(customerServiceCommentDto);
        CustomerServicePost customerServicePost = customerServicePostRepository.findById(inquiryId).orElseThrow(
                CustomerServicePostNotFoundException::new);
        Member member = memberRepository.findById(ADMIN_NUMBER).orElseThrow(MemberNotFoundException::new);

        customerServiceComment.setMember(member);
        customerServiceComment.setCustomerServicePost(customerServicePost);
        customerServiceComment.setCreatedAt(LocalDateTime.now());

        customerServiceCommentRepository.save(customerServiceComment);
    }

}
