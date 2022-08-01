package com.nhnacademy.marketgg.server.service.impl;

import co.elastic.clients.elasticsearch.license.PostResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final CustomerServicePostRepository postRepository;

    @Override
    public void createPost(final PostRequest postRequest, final String type) throws JsonProcessingException {
        postRepository.createPost(postRequest, type);
    }

    @Override
    public List<PostResponse> retrievesPostList(final Integer page, final String type) {
        return postRepository.retrievesPostList(page, type);
    }

    @Override
    public List<PostResponse> retrievesPostListForMe(final Integer page, final String type, final MemberInfo memberInfo) throws JsonProcessingException {
        return postRepository.retrievesPostListForMe(page, type, memberInfo);
    }

    @Override
    public PostResponse retrievePost(final Long postNo, final String type) {
        return postRepository.retrievePost(postNo, type);
    }

    @Override
    public PostResponseForOtoInquiry retrievePostForOtoInquiry(final Long postNo, final String type) {
        return postRepository.retrievePostForOtoInquiry(postNo, type);
    }

    @Override
    public void updatePost(final Long postNo, final PostRequest postRequest, final String type) {
        postRepository.updatePost(postNo, postRequest, type);
    }

    @Override
    public void deletePost(final Long postNo, final String type) {
        postRepository.deletePost(postNo, type);
    }

    @Override
    public List<String> retrieveOtoReason() {
        return postRepository.retrieveReason();
    }

}
