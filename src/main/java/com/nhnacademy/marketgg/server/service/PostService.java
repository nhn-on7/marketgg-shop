package com.nhnacademy.marketgg.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForDetail;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import org.json.simple.parser.ParseException;

import java.util.List;


/**
 * 고객센터 서비스입니다.
 *
 * @version 1.0.0
 */
public interface PostService {

    /**
     * 1:1 문의를 등록하는 메소드입니다.
     *
     * @param memberId    - 등록하는 회원의 식별번호입니다.
     * @param postRequest - 1:1 문의를 등록하기 위한 DTO 객체입니다.
     * @since 1.0.0
     */
    void createPost(final Long memberId, final PostRequest postRequest);

    /**
     * 지정한 게시글의 상세정보를 반환하는 메소드입니다.
     *
     * @param boardNo - 지정한 게시글의 식별번호입니다.
     * @return 지정한 게시글의 상세정보를 반환합니다.
     * @since 1.0.0
     */
    PostResponseForDetail retrievePost(Long boardNo);

    /**
     * 1:1 문의 단건을 조회하는 메소드입니다.
     *
     * @param boardNo - 게시글의 식별번호입니다.
     * @return 조회한 게시글의 정보를 담은 DTO 객체를 반환합니다.
     * @since 1.0.0
     */
    PostResponseForOtoInquiry retrieveOtoInquiryPost(final Long boardNo);

    /**
     * 회원의 1:1 문의 단건을 조회하는 메소드입니다.
     *
     * @param boardNo - 게시글의 식별번호입니다.
     * @param memberId - 조회할 회원의 식별번호입니다.
     * @return 조회한 게시글의 정보를 담은 DTO 객체를 반환합니다.
     * @since 1.0.0
     */
    PostResponseForOtoInquiry retrieveOwnOtoInquiryPost(final Long boardNo, final Long memberId);

    /**
     * 1:1 문의 전체 목록을 조회하는 메소드입니다.
     *
     * @param categoryCode - 페이징 처리를 위한 객체입니다.
     * @param page         - 조회 할 페이지 번호입니다.
     * @return 1:1 문의 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<PostResponse> retrievePostList(final String categoryCode, final Integer page);

    /**
     * 회원 본인의 1:1 전체 목록을 조회하는 메소드입니다.
     *
     * @param page         - 페이징 처리를 위한 페이지 번호입니다.
     * @param categoryCode - 조회할 게시글의 게시판 타입입니다.
     * @param memberId     - 조회하는 회원의 식별번호입니다.
     * @return 회원의 1:1 문의 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<PostResponse> retrieveOwnPostList(final Integer page, final String categoryCode, final Long memberId);

    /**
     * 게시판 타입별로 검색을 진행 할 수 있습니다.
     *
     * @param categoryCode  - 검색을 진행 할 게시판 타입입니다.
     * @param searchRequest - 검색을 진행 할 검색 정보입니다.
     * @return 검색정보로 검색한 결과목록을 반환합니다.
     * @throws ParseException          파싱도중 예외처리입니다.
     * @throws JsonProcessingException JSON 관련 파싱처리 도중 예외처리입니다.
     * @since 1.0.0
     */
    List<PostResponse> searchForCategory(final String categoryCode, final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException;

    /**
     * Reason, Status 와 같은 옵션으로 검색을 진행 할 수 있습니다.
     *
     * @param categoryCode  - 검색을 진행 할 게시판 타입입니다.
     * @param searchRequest - 검색을 진행할 검색 정보입니다.
     * @param option        - 검색을 진행 할 필터 값입니다.
     * @param optionType    - 게시글을 진행 할 필터 타입입니다.
     * @return 검색정보로 검색한 결과목록을 반환합니다.
     * @throws ParseException          파싱도중 예외처리입니다.
     * @throws JsonProcessingException JSON 관련 파싱처리 도중 예외처리입니다.
     * @since 1.0.0
     */
    List<PostResponse> searchForOption(final String categoryCode, final SearchRequest searchRequest,
                                              final String option, final String optionType)
            throws JsonProcessingException, ParseException;

    /**
     * 입력받은 정보로 게시글을 수정 할 수 있습니다.
     *
     * @param memberNo    - 게시글을 수정 할 회원의 식별번호입니다.
     * @param boardNo     - 수정할 게시글의 식별번호입니다.
     * @param postRequest - 수정할 게시글의 정보를 담은 객체입니다.
     * @since 1.0.0
     */
    void updatePost(final Long memberNo, final Long boardNo, final PostRequest postRequest);

    /**
     * 1:1 문의의 상태를 변경하는 메소드입니다.
     *
     * @param inquiryId - 상태를 변경할 1:1 문의의 식별번호입니다.
     * @param status    - 변경할 상태 정보를 담고 있는 DTO 객체입니다.
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
