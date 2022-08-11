package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.server.auth.AuthRepository;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberNameResponse;
import com.nhnacademy.marketgg.server.dto.request.category.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.category.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.customerservice.CommentReady;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForDetail;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForReady;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.elastic.document.ElasticBoard;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticBoardRepository;
import com.nhnacademy.marketgg.server.elastic.repository.SearchRepository;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.cart.customerservicecomment.CustomerServiceCommentRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.post.DefaultPostService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultPostServiceTest {

    @InjectMocks
    DefaultPostService postService;

    @Mock
    CustomerServicePostRepository postRepository;
    @Mock
    ElasticBoardRepository elasticBoardRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    CustomerServiceCommentRepository commentRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    SearchRepository searchRepository;
    @Mock
    AuthRepository authRepository;

    private static final String NOTICE_CODE = "701";
    private static final String OTO_CODE = "702";
    private static final String FAQ_CODE = "703";
    private static final Integer PAGE_SIZE = 10;

    private PostResponse postResponse;
    private PostResponseForReady ready;
    private PostResponseForReady ready2;
    private MemberInfo memberInfo;
    private SearchRequest searchRequest;
    private MemberCreateRequest createRequest;
    private CategoryCreateRequest categoryCreateRequest;
    private CategorizationCreateRequest categorizationCreateRequest;
    private PostRequest postRequest;
    private Cart cart;
    private CustomerServicePost post;
    private ElasticBoard board;
    private Category category;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        postResponse = new PostResponse(1L, FAQ_CODE, "Hello", " ", " ", LocalDateTime.now());
        CommentReady commentReady = new CommentReady("hello", "99990000111122223333444455556666", LocalDateTime.now());
        ready = new PostResponseForReady(2L, FAQ_CODE, "Hello", "hi", "환불", "미답변",
                                         LocalDateTime.now(), LocalDateTime.now(),
                                         List.of(commentReady));

        ready2 = new PostResponseForReady(3L, OTO_CODE, "Hello", "hi", "환불", "미답변",
                                          LocalDateTime.now(), LocalDateTime.now(),
                                          List.of(commentReady));

        searchRequest = new SearchRequest("hello", 0, PAGE_SIZE);
        memberInfo = Dummy.getDummyMemberInfo(1L, cart);
        createRequest = new MemberCreateRequest();
        categoryCreateRequest = new CategoryCreateRequest();
        categorizationCreateRequest = new CategorizationCreateRequest();
        postRequest = new PostRequest();
        Member member = new Member(createRequest, cart);
        category = new Category(categoryCreateRequest, new Categorization(categorizationCreateRequest));
        post = new CustomerServicePost(member, category, postRequest);
        board = new ElasticBoard(post);
    }

    @Test
    @DisplayName("게시글 등록")
    void testCreatePost() {
        ReflectionTestUtils.setField(postRequest, "categoryCode", OTO_CODE);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(new Member(createRequest, cart)));
        given(categoryRepository.findById(anyString())).willReturn(
                Optional.of(new Category(categoryCreateRequest, new Categorization(categorizationCreateRequest))));
        given(postRepository.save(any(CustomerServicePost.class))).willReturn(post);

        postService.createPost(postRequest, memberInfo);

        then(postRepository).should(times(1)).save(any(CustomerServicePost.class));
    }

    @Test
    @DisplayName("게시글 등록 실패(권한 이슈)")
    void testCreatePostNoAccess() {
        ReflectionTestUtils.setField(postRequest, "categoryCode", NOTICE_CODE);
        postService.createPost(postRequest, memberInfo);

        then(postRepository).should(times(0)).save(any(CustomerServicePost.class));
    }

    @Test
    @DisplayName("게시글 목록 조회(1:1 문의)")
    void testRetrievePostList() {
        ReflectionTestUtils.setField(postRequest, "categoryCode", OTO_CODE);
        given(postRepository.findPostByCategoryAndMember(any(PageRequest.class), anyString(), anyLong())).willReturn(
                Page.empty());

        List<PostResponse> list = postService.retrievePostList(OTO_CODE, 0, memberInfo);

        assertThat(list).isEmpty();
    }

    @Test
    @DisplayName("게시글 목록 조회(1:1 문의 X)")
    void testRetrievePostListNotOto() {
        given(postRepository.findPostsByCategoryId(any(PageRequest.class), anyString())).willReturn(Page.empty());

        List<PostResponse> list = postService.retrievePostList(NOTICE_CODE, 0, memberInfo);

        assertThat(list).isEmpty();
    }

    @Test
    @DisplayName("게시글 상세 조회")
    void testRetrievePost() throws Exception {
        ReflectionTestUtils.setField(memberInfo, "roles", Set.of("ROLE_ADMIN"));
        ReflectionTestUtils.setField(categoryCreateRequest, "categoryCode", FAQ_CODE);
        ReflectionTestUtils.setField(postRequest, "categoryCode", FAQ_CODE);
        ReflectionTestUtils.setField(ready, "categoryCode", FAQ_CODE);
        ReflectionTestUtils.setField(category, "id", FAQ_CODE);
        ReflectionTestUtils.setField(post, "category", category);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(postRepository.findByBoardNo(anyLong())).willReturn(ready);
        given(authRepository.getNameListByUuid(any())).willReturn(
                List.of(new MemberNameResponse("99990000111122223333444455556666", "박세완")));

        PostResponseForDetail postResponseForDetail = postService.retrievePost(1L, memberInfo);

        assertThat(postResponseForDetail.getCategoryCode()).isEqualTo(FAQ_CODE);
    }

    @Test
    @DisplayName("게시글 상세 조회 (1:1)")
    void testRetrievePostForOto() throws Exception {
        ReflectionTestUtils.setField(categoryCreateRequest, "categoryCode", OTO_CODE);
        ReflectionTestUtils.setField(postRequest, "categoryCode", OTO_CODE);
        ReflectionTestUtils.setField(category, "id", OTO_CODE);
        ReflectionTestUtils.setField(post, "category", category);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(postRepository.findOwnOtoInquiry(anyLong(), anyLong())).willReturn(ready2);
        given(authRepository.getNameListByUuid(any())).willReturn(
                List.of(new MemberNameResponse("99990000111122223333444455556666", "박세완")));

        PostResponseForDetail postResponseForDetail = postService.retrievePost(1L, memberInfo);

        assertThat(postResponseForDetail.getCategoryCode()).isEqualTo(OTO_CODE);
    }

    @Test
    @DisplayName("카테고리 별 검색")
    void testSearchForCategory() throws Exception {
        ReflectionTestUtils.setField(postRequest, "categoryCode", NOTICE_CODE);
        given(searchRepository.searchBoardWithCategoryCode(anyString(), any(SearchRequest.class),
                                                           anyString())).willReturn(List.of(postResponse));

        List<PostResponse> responses = postService.searchForCategory(NOTICE_CODE, searchRequest, memberInfo);

        assertThat(responses).hasSize(1);
    }

    @Test
    @DisplayName("카테고리 별 검색 (사용자의 1:1 문의 검색 시)")
    void testSearchForCategoryForOto() throws Exception {
        ReflectionTestUtils.setField(postRequest, "categoryCode", OTO_CODE);

        List<PostResponse> responses = postService.searchForCategory(OTO_CODE, searchRequest, memberInfo);

        assertThat(responses).isEmpty();
    }

    @Test
    @DisplayName("옵션 별 검색")
    void testSearchForOption() throws Exception {
        ReflectionTestUtils.setField(postRequest, "categoryCode", OTO_CODE);
        given(searchRepository.searchBoardWithOption(anyString(), anyString(), any(SearchRequest.class),
                                                     anyString())).willReturn(List.of(postResponse));
        List<PostResponse> responses = postService.searchForOption(OTO_CODE, searchRequest, "reason", "배송");

        assertThat(responses).hasSize(1);
    }

    @Test
    @DisplayName("게시글 수정")
    void testUpdatePost() {
        ReflectionTestUtils.setField(postRequest, "categoryCode", NOTICE_CODE);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(postRepository.save(any(CustomerServicePost.class))).willReturn(post);
        postService.updatePost(NOTICE_CODE, 1L, postRequest);

        then(postRepository).should(times(1)).save(any(CustomerServicePost.class));
    }

    @Test
    @DisplayName("1:1 문의 상태 변경")
    void testUpdateOtoInquiryStatus() {
        PostStatusUpdateRequest updateRequest = new PostStatusUpdateRequest();
        ReflectionTestUtils.setField(updateRequest, "status", "hi");
        ReflectionTestUtils.setField(postRequest, "categoryCode", OTO_CODE);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(postRepository.save(any(CustomerServicePost.class))).willReturn(post);

        postService.updateOtoInquiryStatus(1L, new PostStatusUpdateRequest());

        then(postRepository).should(times(1)).save(any(CustomerServicePost.class));
    }

    @Test
    @DisplayName("게시글 삭제")
    void testDeletePost() {
        ReflectionTestUtils.setField(postRequest, "categoryCode", OTO_CODE);
        willDoNothing().given(postRepository).deleteById(anyLong());
        willDoNothing().given(elasticBoardRepository).deleteById(anyLong());
        willDoNothing().given(commentRepository).deleteAllByCustomerServicePost_Id(anyLong());

        postService.deletePost(OTO_CODE, 1L, memberInfo);

        then(postRepository).should(times(1)).deleteById(anyLong());
        then(elasticBoardRepository).should(times(1)).deleteById(anyLong());
        then(commentRepository).should(times(1)).deleteAllByCustomerServicePost_Id(anyLong());
    }

    @Test
    @DisplayName("관리자의 게시글 삭제")
    void testDeletePostForAdmin() {
        ReflectionTestUtils.setField(memberInfo, "roles", Set.of("ROLE_ADMIN"));
        ReflectionTestUtils.setField(postRequest, "categoryCode", FAQ_CODE);
        willDoNothing().given(postRepository).deleteById(anyLong());
        willDoNothing().given(elasticBoardRepository).deleteById(anyLong());
        willDoNothing().given(commentRepository).deleteAllByCustomerServicePost_Id(anyLong());

        postService.deletePost(FAQ_CODE, 1L, memberInfo);

        then(postRepository).should(times(1)).deleteById(anyLong());
        then(elasticBoardRepository).should(times(1)).deleteById(anyLong());
        then(commentRepository).should(times(1)).deleteAllByCustomerServicePost_Id(anyLong());
    }

}
