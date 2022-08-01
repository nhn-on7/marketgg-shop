package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.server.elastic.document.ElasticBoard;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticBoardRepository;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
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
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

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
public class DefaultPostServiceTest {

    @InjectMocks
    DefaultPostService postService;

    @Mock
    ElasticBoardRepository elasticBoardRepository;

    @Mock
    CustomerServicePostRepository postRepository;

    @Mock
    CustomerServiceCommentRepository commentRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    CategoryRepository categoryRepository;

    private static Category category;
    private static Member member;

    @BeforeAll
    static void beforeAll() {
        MemberCreateRequest memberRequest = new MemberCreateRequest();
        CategoryCreateRequest categoryRequest = new CategoryCreateRequest();
        CategorizationCreateRequest categorizationRequest = new CategorizationCreateRequest();
        member = new Member(memberRequest, new Cart());
        Categorization categorization = new Categorization(categorizationRequest);
        category = new Category(categoryRequest, categorization);
    }

    @Test
    @DisplayName("1:1 문의 등록")
    void testCreateOtoInquiry() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(categoryRepository.findById(anyString())).willReturn(Optional.of(category));
        given(categoryRepository.retrieveCategoryIdByName(anyString())).willReturn("702");

        postService.createPost(1L, new PostRequest());

        then(postRepository).should().save(any(CustomerServicePost.class));
        then(elasticBoardRepository).should().save(any(ElasticBoard.class));
    }

    @Test
    @DisplayName("1:1 문의 단건 조회")
    void testRetrieveCustomerServicePost() {
        PostResponseForOtoInquiry otoInquiry = new PostResponseForOtoInquiry();
        ReflectionTestUtils.setField(otoInquiry, "id", 1L);

        given(postRepository.findOtoInquiryById(anyLong())).willReturn(otoInquiry);
        given(commentRepository.findByInquiryId(anyLong())).willReturn(List.of());

        postService.retrieveOtoInquiryPost(1L);

        then(postRepository).should().findOtoInquiryById(anyLong());
        then(commentRepository).should().findByInquiryId(anyLong());
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void testRetrieveOtoInquiries() {
        given(postRepository.findPostsByCategoryId(any(Pageable.class), anyString()))
                .willReturn(Page.empty());

        postService.retrievePostList("702", 0);

        then(postRepository).should().findPostsByCategoryId(any(Pageable.class), anyString());
    }

    @Test
    @DisplayName("특정 회원의 1:1 문의 목록 조회")
    void testRetrieveOwnOtoInquiries() {
        given(postRepository.findPostByCategoryAndMember(any(Pageable.class), anyString(), anyLong()))
                .willReturn(Page.empty());

        postService.retrieveOwnPostList(0, "702", 1L);

        then(postRepository).should()
                            .findPostByCategoryAndMember(any(Pageable.class), anyString(), anyLong());
    }

    @Test
    @DisplayName("1:1 문의 삭제(댓글이 없을 경우)")
    void testDeleteCustomerServicePostNotExistsComments() {
        CustomerServicePost csPost = new CustomerServicePost(member, category, new PostRequest());

        given(postRepository.findById(anyLong())).willReturn(Optional.of(csPost));
        given(commentRepository.findCommentIdsByInquiryId(anyLong())).willReturn(List.of());

        willDoNothing().given(elasticBoardRepository).deleteById(anyLong());
        willDoNothing().given(postRepository).delete(any(CustomerServicePost.class));

        postRepository.save(csPost);
        postService.deletePost(1L);

        then(postRepository).should().findById(anyLong());
        then(postRepository).should().delete(any(CustomerServicePost.class));
        then(commentRepository).should().findCommentIdsByInquiryId(anyLong());
    }

}
