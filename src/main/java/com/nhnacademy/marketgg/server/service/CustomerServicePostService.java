package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * 고객센터 서비스입니다.
 *
 * @version 1.0.0
 */
public interface CustomerServicePostService {

    /**
     * 1:1 문의를 등록하는 메소드입니다.
     *
     * @param memberId               - 등록하는 회원의 식별번호입니다.
     * @param postRequest - 1:1 문의를 등록하기 위한 DTO 객체입니다.
     */
    void createPost(final Long memberId, final PostRequest postRequest);

    /**
     * 고객센터 게시글 단건을 조회하는 메소드입니다.
     *
     * @param csPostId - 고객센터 게시글의 식별번호입니다.
     * @return 조회한 게시글의 정보를 담은 DTO 객체를 반환합니다.
     * @since 1.0.0
     */
    PostResponseForOtoInquiry retrievePost(final Long csPostId);

    /**
     * 1:1 문의 전체 목록을 조회하는 메소드입니다.
     *
     * @param categoryCode - 페이징 처리를 위한 객체입니다.
     * @param page - 조회 할 페이지 번호입니다.
     * @return 1:1 문의 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<PostResponse> retrievePostList(final String categoryCode, final Integer page);

    /**
     * 회원 본인의 1:1 전체 목록을 조회하는 메소드입니다.
     *
     * @param page - 페이징 처리를 위한 페이지 번호입니다.
     * @param categoryCode - 조회할 게시글의 게시판 타입입니다.
     * @param memberId - 조회하는 회원의 식별번호입니다.
     * @return 회원의 1:1 문의 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<PostResponse> retrievesOwnPostList(final Integer page, final String categoryCode, final Long memberId);

    void updatePost(final Long memberNo, final Long boardNo, final PostRequest postRequest);

    /**
     * 1:1 문의의 상태를 변경하는 메소드입니다.
     *
     * @param inquiryId - 상태를 변경할 1:1 문의의 식별번호입니다.
     * @param status - 변경할 상태 정보를 담고 있는 DTO 객체입니다.
     * @since 1.0.0
     */
    void updateInquiryStatus(Long inquiryId, PostStatusUpdateRequest status);

    /**
     * 고객센터 게시글을 삭제하는 메소드입니다.
     *
     * @param csPostId - 삭제할 고객센터 게시글의 식별번호입니다.
     * @since 1.0.0
     */
    void deletePost(Long csPostId);

}
