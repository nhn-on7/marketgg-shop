package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.CustomerServiceCommentDto;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.mapper.impl.CustomerServiceCommentMapper;
import com.nhnacademy.marketgg.server.repository.customerservicecomment.CustomerServiceCommentRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@Transactional
public class DefaultCustomerServiceCommentServiceTest {

    @InjectMocks
    DefaultCustomerServiceCommentService commentService;

    @Mock
    CustomerServiceCommentMapper commentMapper;

    @Mock
    CustomerServiceCommentRepository commentRepository;

    @Mock
    CustomerServicePostRepository postRepository;

    @Mock
    MemberRepository memberRepository;

     private static CustomerServiceComment comment;
     private static CustomerServicePost post;
     private static Member member;

    @BeforeAll
    static void beforeAll() {
        member = new Member(new MemberCreateRequest(), new Cart());
        post = new CustomerServicePost(1L, member, null, "c", "t", "r", "s", LocalDateTime.now(), LocalDateTime.now());
        comment = new CustomerServiceComment(1L, null, member, null, LocalDateTime.now());
    }

    @Test
    @DisplayName("댓글 등록")
    void testCreateComment() {
        given(commentMapper.toEntity(any(CustomerServiceCommentDto.class))).willReturn(comment);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        commentService.createComment(1L, 1L, new CustomerServiceCommentDto());

        then(commentRepository).should().save(any(CustomerServiceComment.class));
    }

    @Test
    @DisplayName("댓글 단건 조회")
    void testRetrieveComment() {
        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

        commentService.retrieveComment(1L);

        then(commentRepository).should().findById(anyLong());
    }

    @Test
    @DisplayName("게시글의 댓글 목록 조회")
    void testRetrieveCommentsByInquiry() {
        given(commentRepository.findByInquiry(anyLong())).willReturn(List.of());

        commentService.retrieveCommentsByInquiry(1L);

        then(commentRepository).should().findByInquiry(anyLong());
    }

}
