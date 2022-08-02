package com.nhnacademy.marketgg.server.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.PostResponse;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.elastic.document.ElasticBoard;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticBoardRepository;
import com.nhnacademy.marketgg.server.elastic.repository.SearchRepository;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.customerservicecomment.CustomerServiceCommentRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

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
    SearchRepository searchRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    CategoryRepository categoryRepository;

    static List<PostResponse> list;

    @BeforeAll
    static void beforeAll() {
        list = List.of(Dummy.getDummyPostResponse());
    }

    @Test
    @DisplayName("1:1 문의 등록")
    void testCreatePost() {
        given(memberRepository.findById(anyLong())).willReturn(
                Optional.of(Dummy.getDummyMember(Dummy.getDummyCart(1L))));
        given(categoryRepository.findById(anyString())).willReturn(Optional.of(Dummy.getDummyCategory()));
        given(categoryRepository.retrieveCategoryIdByName(anyString())).willReturn("702");

        postService.createPost(1L, new PostRequest());

        then(postRepository).should().save(any(CustomerServicePost.class));
        then(elasticBoardRepository).should().save(any(ElasticBoard.class));
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void testRetrievePost() {
        given(postRepository.findByBoardNo(anyLong())).willReturn(Dummy.getDummyPostResponseForDetail());

        postService.retrievePost(1L);

        then(postRepository).should(times(1)).findByBoardNo(anyLong());
    }

    @Test
    @DisplayName("1:1 문의 단건 조회")
    void testRetrieveOtoInquiryPost() {
        given(postRepository.findOtoInquiryById(anyLong())).willReturn(Dummy.getDummyPostResponseForOtoInquiry());

        postService.retrieveOtoInquiryPost(1L);

        then(postRepository).should(times(1)).findOtoInquiryById(anyLong());
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void testRetrievePostList() {
        given(postRepository.findPostsByCategoryId(any(Pageable.class), anyString()))
                .willReturn(new PageImpl<>(list, PageRequest.of(0, 10), 1));

        postService.retrievePostList("701", 0);

        then(postRepository).should(times(1))
                            .findPostsByCategoryId(any(Pageable.class), anyString());
    }

    @Test
    @DisplayName("특정 회원의 게시글 목록 조회")
    void testRetrieveOwnPostList() {
        given(postRepository.findPostByCategoryAndMember(any(Pageable.class), anyString(), anyLong()))
                .willReturn(new PageImpl<>(list, PageRequest.of(0, 10), 1));

        postService.retrieveOwnPostList(0, "701", 1L);

        then(postRepository).should(times(1))
                            .findPostByCategoryAndMember(any(Pageable.class), anyString(), anyLong());
    }

    @Test
    @DisplayName("게시판 타입(카테고리)으로 게시글 검색")
    void testSearchForCategory() throws ParseException, JsonProcessingException {
        given(searchRepository.searchBoardWithCategoryCode(anyString(), any(SearchRequest.class), anyString()))
                .willReturn(List.of(Dummy.getSearchBoardResponse()));

        postService.searchForCategory("701", Dummy.getSearchRequest());

        then(searchRepository).should(times(1))
                              .searchBoardWithCategoryCode(anyString(), any(SearchRequest.class), anyString());
    }

    @Test
    @DisplayName("옵션(상태, 사유)으로 게시글 검색")
    void testSearchForOption() throws ParseException, JsonProcessingException {
        given(searchRepository.searchBoardWithOption(anyString(), anyString(), any(SearchRequest.class), anyString()))
                .willReturn(List.of(Dummy.getSearchBoardResponse()));

        postService.searchForOption("701", Dummy.getSearchRequest(), "회원", "reason");

        then(searchRepository).should(times(1))
                .searchBoardWithOption(anyString(), anyString(), any(SearchRequest.class), anyString());
    }

    @Test
    @DisplayName("게시글 수정")
    void testUpdatePost() {
        CustomerServicePost dummyPost = Dummy.getCustomerServicePost();

        given(postRepository.findById(anyLong())).willReturn(Optional.of(dummyPost));

        postService.updatePost(1L, dummyPost.getId(), Dummy.getPostRequest());

        then(postRepository).should(times(1)).findById(anyLong());
        then(postRepository).should(times(1)).save(any(CustomerServicePost.class));
    }

    @Test
    @DisplayName("1:1 문의 상태 변경")
    void testUpdateInquiryStatus() {
        CustomerServicePost dummyPost = Dummy.getCustomerServicePost();
        ElasticBoard dummyboard = Dummy.getElasticBoard();

        given(postRepository.findById(anyLong())).willReturn(Optional.of(dummyPost));
        given(elasticBoardRepository.findById(anyLong())).willReturn(Optional.of(dummyboard));

        postService.updateInquiryStatus(1L, Dummy.getPostStatusUpdateRequest());

        then(postRepository).should(times(1)).findById(anyLong());
        then(elasticBoardRepository).should(times(1)).findById(anyLong());
        then(postRepository).should(times(1)).save(any(CustomerServicePost.class));
        then(elasticBoardRepository).should(times(1)).save(any(ElasticBoard.class));

    }

    @Test
    @DisplayName("게시글 삭제")
    void testDeletePost() {
        CustomerServicePost dummyPost = Dummy.getCustomerServicePost();
        List<Long> commentList = List.of(1L);

        given(postRepository.findById(anyLong())).willReturn(Optional.of(dummyPost));
        given(commentRepository.findCommentIdsByInquiryId(anyLong())).willReturn(commentList);

        postService.deletePost(1L);

        then(commentRepository).should(times(1)).findCommentIdsByInquiryId(anyLong());
        then(postRepository).should(times(1)).delete(any(CustomerServicePost.class));
        then(elasticBoardRepository).should(times(1)).deleteById(anyLong());
    }

}
