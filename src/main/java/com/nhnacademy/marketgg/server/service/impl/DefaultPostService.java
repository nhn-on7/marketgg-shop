package com.nhnacademy.marketgg.server.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForDetail;
import com.nhnacademy.marketgg.server.elastic.document.ElasticBoard;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticBoardRepository;
import com.nhnacademy.marketgg.server.elastic.repository.SearchRepository;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.category.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.exception.customerservicepost.CustomerServicePostNotFoundException;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.customerservicecomment.CustomerServiceCommentRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final CustomerServicePostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final CustomerServiceCommentRepository commentRepository;
    private final ElasticBoardRepository elasticBoardRepository;
    private final SearchRepository searchRepository;

    private static final String OTO_INQUIRY = "1:1문의";
    private static final Integer PAGE_SIZE = 10;

    @Override
    public void createPost(final PostRequest postRequest, final MemberInfo memberInfo) {
        
    }

    @Override
    public List<PostResponse> retrievePostList(final String categoryCode, final Integer page,
                                               final MemberInfo memberInfo) {
        return null;
    }

    @Override
    public PostResponseForDetail retrievePost(final Long postNo, final MemberInfo memberInfo) {
        return null;
    }

    @Override
    public List<PostResponse> searchForCategory(final String categoryCode, final SearchRequest searchRequest,
                                                final MemberInfo memberInfo)
            throws ParseException, JsonProcessingException {
        return null;
    }

    @Override
    public List<PostResponse> searchForOption(final String categoryCode, final SearchRequest searchRequest,
                                              final String optionType, final String option)
            throws JsonProcessingException, ParseException {
        return null;
    }

    @Override
    public void updatePost(final String categoryCode, final Long postNo, final PostRequest postRequest) {

    }

    @Override
    public void updateOtoInquiryStatus(final Long postNo, final PostStatusUpdateRequest statusUpdateRequest) {

    }

    @Override
    public void deletePost(final String categoryCode, final Long postNo, final MemberInfo memberInfo) {

    }

    // @Transactional
    // @Override
    // public void createPost(final ) {
    //     Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    //     String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY);
    //     Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
    //
    //     CustomerServicePost csPost = new CustomerServicePost(member, category, postRequest);
    //
    //     postRepository.save(csPost);
    //     elasticBoardRepository.save(new ElasticBoard(csPost));
    // }
    //
    // @Override
    // public List<PostResponse> retrievePostList(final String categoryCode, final Integer page) {
    //     return postRepository.findPostsByCategoryId(PageRequest.of(page, PAGE_SIZE), categoryCode).getContent();
    // }
    //
    // @Override
    // public PostResponseForDetail retrievePost(final Long boardNo) {
    //     return postRepository.findByBoardNo(boardNo);
    // }
    //
    // @Override
    // public List<PostResponse> searchForCategory(final String categoryCode, final SearchRequest searchRequest)
    //         throws ParseException, JsonProcessingException {
    //
    //     return searchRepository.searchBoardWithCategoryCode(categoryCode, searchRequest, "categoryCode");
    // }
    //
    // @Override
    // public List<PostResponse> searchForOption(final String categoryCode, final SearchRequest searchRequest,
    //                                           final String option, final String optionType)
    //         throws JsonProcessingException, ParseException {
    //
    //     return searchRepository.searchBoardWithOption(categoryCode, option, searchRequest, optionType);
    // }
    //
    // @Transactional
    // @Override
    // public void updatePost(final Long memberNo, final Long boardNo, final PostRequest postRequest) {
    //     CustomerServicePost post = postRepository.findById(boardNo)
    //                                              .orElseThrow(CustomerServicePostNotFoundException::new);
    //     post.updatePost(postRequest);
    //
    //     postRepository.save(post);
    // }
    //
    // @Transactional
    // @Override
    // public void updateOtoInquiryStatus(final Long boardNo, final PostStatusUpdateRequest status) {
    //     CustomerServicePost board = postRepository.findById(boardNo)
    //                                               .orElseThrow(CustomerServicePostNotFoundException::new);
    //     ElasticBoard elasticBoard = elasticBoardRepository.findById(boardNo)
    //                                                       .orElseThrow(CustomerServicePostNotFoundException::new);
    //
    //     board.updatePostStatus(status.getStatus());
    //     elasticBoard.setStatus(status.getStatus());
    //
    //     postRepository.save(board);
    //     elasticBoardRepository.save(elasticBoard);
    // }
    //
    // @Transactional
    // @Override
    // public void deletePost(final Long boardNo) {
    //     CustomerServicePost post = postRepository.findById(boardNo)
    //                                              .orElseThrow(CustomerServicePostNotFoundException::new);
    //     List<Long> commentIds = commentRepository.findCommentIdsByInquiryId(boardNo);
    //
    //     commentRepository.deleteAllById(commentIds);
    //     postRepository.delete(post);
    //     elasticBoardRepository.deleteById(boardNo);
    // }

}
