package com.nhnacademy.marketgg.server.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForDetail;
import com.nhnacademy.marketgg.server.elastic.document.ElasticBoard;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.dto.request.searchutil.Bool;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticBoardRepository;
import com.nhnacademy.marketgg.server.elastic.repository.SearchRepository;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.category.CategoryNotFoundException;
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

    private static final String NOTICE_CODE = "701";
    private static final String OTO_CODE = "702";
    private static final String FAQ_CODE = "703";
    private static final Integer PAGE_SIZE = 10;

    @Transactional
    @Override
    public void createPost(final PostRequest postRequest, final MemberInfo memberInfo) {
        if (Boolean.TRUE.equals(this.isAccess(memberInfo, postRequest.getCategoryCode(), "create"))) {
            Member member = memberRepository.findById(memberInfo.getId()).orElseThrow(MemberNotFoundException::new);
            Category category = categoryRepository.findById(postRequest.getCategoryCode()).orElseThrow(
                    CategoryNotFoundException::new);
            CustomerServicePost post = new CustomerServicePost(member, category, postRequest);

            CustomerServicePost savePost = postRepository.save(post);
            elasticBoardRepository.save(new ElasticBoard(savePost));
        }
    }

    @Override
    public List<PostResponse> retrievePostList(final String categoryCode, final Integer page,
                                               final MemberInfo memberInfo) {
        if(Boolean.TRUE.equals(this.isAccess(memberInfo, categoryCode, "retrieveList"))) {
            return postRepository.findPostsByCategoryId(PageRequest.of(page, PAGE_SIZE), categoryCode).getContent();
        }
        return postRepository.findPostByCategoryAndMember(PageRequest.of(page, PAGE_SIZE), categoryCode, memberInfo.getId()).getContent();
    }

    @Override
    public PostResponseForDetail retrievePost(final Long postNo, final MemberInfo memberInfo) {
        PostResponseForDetail postResponseForDetail = new retrieve


        return postRepository.findByb;
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

    private Boolean isAccess(final MemberInfo memberInfo, final String categoryCode, final String option) {
        List<String> create = List.of(NOTICE_CODE, FAQ_CODE);
        List<String> retrieveList = List.of(OTO_CODE);

        switch (option) {
            case "create": {
                return (memberInfo.isAdmin()) || !create.contains(categoryCode);
            }
            case "retrieveList": {
                return memberInfo.isAdmin() || !retrieveList.contains(categoryCode);
            }
            case "retrieve": {
                return true;
            }
            default: return false;
        }
    }

}
