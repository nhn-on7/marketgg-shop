package com.nhnacademy.marketgg.server.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CommentRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import com.nhnacademy.marketgg.server.dto.response.CommentResponse;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.repository.customerservicecomment.CustomerServiceCommentRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultCustomerServiceCommentServiceTest {

    @InjectMocks
    DefaultCustomerServiceCommentService commentService;

    @Mock
    CustomerServiceCommentRepository commentRepository;

    @Mock
    CustomerServicePostRepository postRepository;

    @Mock
    MemberRepository memberRepository;

    private static CustomerServicePost post;
    private static Member member;

    @BeforeAll
    static void beforeAll() {
        Categorization categorization = new Categorization(new CategorizationCreateRequest());
        Category category = new Category(new CategoryCreateRequest(), categorization);
        member = new Member(new MemberCreateRequest(), new Cart());

        post = new CustomerServicePost(member, category, new PostRequest());
    }

    @Test
    @DisplayName("댓글 등록")
    void testCreateComment() {
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        commentService.createComment(1L, 1L, new CommentRequest());

        then(commentRepository).should().save(any(CustomerServiceComment.class));
    }

    @Test
    @DisplayName("댓글 단건 조회")
    void testRetrieveComment() {
        given(commentRepository.findCommentById(anyLong())).willReturn(
            new CommentResponse(1L, 1L, 1L, "content", LocalDateTime.now()));

        commentService.retrieveComment(1L);

        then(commentRepository).should().findCommentById(anyLong());
    }

    @Test
    @DisplayName("게시글의 댓글 목록 조회")
    void testRetrieveCommentsByInquiry() {
        given(commentRepository.findByInquiryId(anyLong())).willReturn(List.of());

        commentService.retrieveCommentsByInquiry(1L);

        then(commentRepository).should().findByInquiryId(anyLong());
    }

}
