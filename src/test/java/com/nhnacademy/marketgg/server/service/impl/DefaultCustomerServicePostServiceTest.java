package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostDto;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
@Transactional
public class DefaultCustomerServicePostServiceTest {

    @InjectMocks
    DefaultCustomerServicePostService postService;

    @Mock
    CustomerServicePostRepository postRepository;

    @Mock
    CustomerServiceCommentRepository commentRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CustomerServicePostMapper postMapper;

    private static CustomerServicePost post;
    private static Category category;
    private static Member member;
    private static CustomerServicePostDto postDto;

    @BeforeAll
    static void beforeAll() {
        postDto = new CustomerServicePostDto();
        MemberCreateRequest memberRequest = new MemberCreateRequest();
        CategoryCreateRequest categoryRequest = new CategoryCreateRequest();
        CategorizationCreateRequest categorizationRequest = new CategorizationCreateRequest();
        member = new Member(memberRequest, new Cart());
        Categorization categorization = new Categorization(categorizationRequest);
        category = new Category(categoryRequest, categorization);
        post = new CustomerServicePost(1L, member, category,
                                       null, null, null, null,
                                       LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("1:1 문의 등록")
    void testCreateOtoInquiry() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(categoryRepository.findById(anyString())).willReturn(Optional.of(category));
        given(categoryRepository.retrieveCategoryIdByName(anyString())).willReturn("702");
        given(postMapper.toEntity(any(CustomerServicePostDto.class))).willReturn(post);

        postService.createOtoInquiry(1L, postDto);

        then(postRepository).should().save(any(CustomerServicePost.class));
    }

    @Test
    @DisplayName("고객센터 게시글 단건 조회")
    void testRetrieveCustomerServicePost() {
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(postMapper.toDto(any(CustomerServicePost.class))).willReturn(postDto);

        postService.retrieveCustomerServicePost(1L);

        then(postRepository).should().findById(anyLong());
    }

    @Test
    @DisplayName("1:1 문의 목록 조회")
    void testRetrieveOtoInquiries() {
        given(categoryRepository.retrieveCategoryIdByName(anyString())).willReturn("702");
        given(postRepository.findPostsByCategoryId(any(Pageable.class), anyString()))
                .willReturn(Page.empty());

        postService.retrieveOtoInquiries(PageRequest.of(0, 10));

        then(postRepository).should().findPostsByCategoryId(any(Pageable.class), anyString());
    }

    @Test
    @DisplayName("특정 회원의 1:1 문의 목록 조회")
    void testRetrieveOwnOtoInquiries() {
        given(categoryRepository.retrieveCategoryIdByName(anyString())).willReturn("702");
        given(postRepository.findPostByCategoryAndMember(any(Pageable.class), anyString(), anyLong()))
                .willReturn(Page.empty());

        postService.retrieveOwnOtoInquiries(PageRequest.of(0, 10), 1L);

        then(postRepository).should()
                            .findPostByCategoryAndMember(any(Pageable.class), anyString(), anyLong());
    }

    @Test
    @DisplayName("고객센터 게시글 삭제(댓글이 없을 경우)")
    void testDeleteCustomerServicePostNotExistsComments() {
        given(postRepository.findById(anyLong())).willReturn(Optional.ofNullable(post));
        given(commentRepository.findByInquiry(anyLong())).willReturn(List.of());

        willDoNothing().given(postRepository).delete(any(CustomerServicePost.class));

        postService.deleteCustomerServicePost(1L);

        then(postRepository).should().findById(anyLong());
        then(postRepository).should().delete(any(CustomerServicePost.class));
        then(commentRepository).should().findByInquiry(anyLong());
    }

    @Test
    @DisplayName("고객센터 게시글 삭제(댓글이 있을 경우)")
    void testDeleteCustomerServicePostExistsComments() {
        CustomerServiceComment customerServiceComment = new CustomerServiceComment(1L, post, member,
                                                                                   "content", LocalDateTime.now());
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(commentRepository.findByInquiry(anyLong())).willReturn(List.of(customerServiceComment));

        willDoNothing().given(postRepository).delete(any(CustomerServicePost.class));
        willDoNothing().given(commentRepository).deleteAll(List.of(customerServiceComment));

        postService.deleteCustomerServicePost(1L);

        then(postRepository).should().findById(anyLong());
        then(postRepository).should().delete(any(CustomerServicePost.class));
        then(commentRepository).should().findByInquiry(anyLong());
    }
}
