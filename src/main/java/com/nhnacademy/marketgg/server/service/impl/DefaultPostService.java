package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.CommentResponse;
import com.nhnacademy.marketgg.server.dto.response.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.server.elastic.document.ElasticBoard;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticBoardRepository;
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
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultPostService implements CustomerServicePostService {

    private final CustomerServicePostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final CustomerServiceCommentRepository commentRepository;
    private final ElasticBoardRepository elasticBoardRepository;

    private static final String OTO_INQUIRY = "1:1문의";

    @Transactional
    @Override
    public void createPost(final Long memberId, final PostRequest postRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY);
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        CustomerServicePost csPost = new CustomerServicePost(member, category, postRequest);

        postRepository.save(csPost);
        elasticBoardRepository.save(new ElasticBoard(csPost));
    }

    @Override
    public PostResponseForOtoInquiry retrievePost(final Long inquiryId) {
        PostResponseForOtoInquiry otoInquiry = postRepository.findOtoInquiryById(inquiryId);

        return addCommentList(otoInquiry);
    }

    @Override
    public List<PostResponse> retrievePostList(final String categoryCode, final Integer page) {
        return postRepository.findPostsByCategoryId(PageRequest.of(page, 10), categoryCode).getContent();
    }

    @Override
    public List<PostResponse> retrievesOwnPostList(final Integer page, final String categoryCode, final Long memberId) {
        return postRepository.findPostByCategoryAndMember(PageRequest.of(page, 10), categoryCode, memberId).getContent();
    }

    private PostResponseForOtoInquiry addCommentList(final PostResponseForOtoInquiry otoInquiry) {
        List<CommentResponse> commentList = commentRepository.findByInquiryId(otoInquiry.getId());

        return PostResponseForOtoInquiry.builder().otoInquiry(otoInquiry).commentList(commentList).build();
    }

    @Transactional
    @Override
    public void updatePost(final Long memberNo, final Long boardNo, final PostRequest postRequest) {
        CustomerServicePost post = postRepository.findById(boardNo).orElseThrow(CustomerServicePostNotFoundException::new);
        post.updatePost(postRequest);

        postRepository.save(post);
    }

    @Transactional
    @Override
    public void updateInquiryStatus(final Long boardNo, final PostStatusUpdateRequest status) {
        CustomerServicePost board = postRepository.findById(boardNo).orElseThrow(CustomerServicePostNotFoundException::new);
        ElasticBoard elasticBoard = elasticBoardRepository.findById(boardNo).orElseThrow(CustomerServicePostNotFoundException::new);

        board.updatePostStatus(status.getStatus());
        elasticBoard.setStatus(status.getStatus());

        postRepository.save(board);
        elasticBoardRepository.save(elasticBoard);
    }

    @Transactional
    @Override
    public void deletePost(final Long boardNo) {
        CustomerServicePost otoInquiry = postRepository.findById(boardNo).orElseThrow(CustomerServicePostNotFoundException::new);
        List<Long> commentIds = commentRepository.findByInquiryId(boardNo)
                                                 .stream()
                                                 .map(CommentResponse::getId)
                                                 .collect(Collectors.toList());

        commentRepository.deleteAllById(commentIds);
        postRepository.delete(otoInquiry);
        elasticBoardRepository.deleteById(boardNo);
    }

}
