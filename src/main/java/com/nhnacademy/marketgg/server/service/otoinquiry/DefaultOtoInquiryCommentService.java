package com.nhnacademy.marketgg.server.service.otoinquiry;

import com.nhnacademy.marketgg.server.dto.request.customerservice.CommentRequest;
import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.customerservicepost.CustomerServicePostNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.cart.customerservicecomment.CustomerServiceCommentRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultOtoInquiryCommentService implements OtoInquiryCommentService {

    private final CustomerServiceCommentRepository customerServiceCommentRepository;
    private final CustomerServicePostRepository customerServicePostRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void createComment(final Long inquiryId, final Long memberId, final CommentRequest commentRequest) {
        CustomerServicePost csPost
            = customerServicePostRepository.findById(inquiryId)
                                           .orElseThrow(CustomerServicePostNotFoundException::new);

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        CustomerServiceComment comment = new CustomerServiceComment(csPost, member, commentRequest);

        customerServiceCommentRepository.save(comment);
    }

}
