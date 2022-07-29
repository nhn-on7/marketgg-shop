package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.constant.CustomerServicePostStatus;
import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostDto;
import com.nhnacademy.marketgg.server.elastic.document.ElasticBoard;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticBoardRepository;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.category.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.exception.customerservicepost.CustomerServicePostNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.mapper.impl.CustomerServicePostMapper;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.customerservicecomment.CustomerServiceCommentRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultCustomerServicePostService implements CustomerServicePostService {

    private final CustomerServicePostMapper customerServicePostMapper;
    private final CustomerServicePostRepository customerServicePostRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final CustomerServiceCommentRepository customerServiceCommentRepository;
    private final ElasticBoardRepository elasticBoardRepository;

    private static final String OTO_INQUIRY = "1:1문의";

    @Transactional
    @Override
    public void createOtoInquiry(final Long memberId, final CustomerServicePostDto csPostDto) {
        CustomerServicePost customerServicePost = customerServicePostMapper.toEntity(csPostDto);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY);
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        customerServicePost.setMember(member);
        customerServicePost.setCategory(category);
        customerServicePost.setStatus(CustomerServicePostStatus.UNANSWERED.status());
        customerServicePost.setCreatedAt(LocalDateTime.now());
        customerServicePost.setUpdatedAt(LocalDateTime.now());

        customerServicePostRepository.save(customerServicePost);
        elasticBoardRepository.save(new ElasticBoard(customerServicePost));
    }

    @Override
    public CustomerServicePostDto retrieveCustomerServicePost(final Long csPostId) {
        CustomerServicePost csPost = customerServicePostRepository.findById(csPostId)
                                                                  .orElseThrow(
                                                                          CustomerServicePostNotFoundException::new);

        return customerServicePostMapper.toDto(csPost);
    }

    @Override
    public List<CustomerServicePostDto> retrieveOtoInquiries(final Pageable pageable) {
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY);
        List<CustomerServicePost> otoInquiries = customerServicePostRepository.findPostsByCategoryId(pageable,
                                                                                                     categoryId)
                                                                              .getContent();

        return otoInquiries.stream().map(customerServicePostMapper::toDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<CustomerServicePostDto> retrieveOwnOtoInquiries(final Pageable pageable, final Long memberId) {
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY);

        List<CustomerServicePost> ownOtoInquiries = customerServicePostRepository.findPostByCategoryAndMember(pageable,
                                                                                                              categoryId,
                                                                                                              memberId)
                                                                                 .getContent();

        return ownOtoInquiries.stream().map(customerServicePostMapper::toDto).collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    @Override
    public void deleteCustomerServicePost(final Long csPostId) {
        CustomerServicePost otoInquiry = customerServicePostRepository.findById(csPostId)
                                                                      .orElseThrow(
                                                                              CustomerServicePostNotFoundException::new);
        List<CustomerServiceComment> comments = customerServiceCommentRepository.findByInquiry(csPostId);

        customerServiceCommentRepository.deleteAll(comments);
        customerServicePostRepository.delete(otoInquiry);

        elasticBoardRepository.deleteById(csPostId);
    }

}
