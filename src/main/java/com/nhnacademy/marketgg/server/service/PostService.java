package com.nhnacademy.marketgg.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForDetail;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForReady;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import java.util.List;
import org.json.simple.parser.ParseException;


/**
 * 고객센터 서비스입니다.
 *
 * @author 박세완, 김정민
 * @version 1.0.0
 */
public interface PostService {

    /**
     * 1:1 문의를 등록하는 메소드입니다.
     *
     * @param memberInfo  - 등록을 진행할 회원정보입니다.
     * @param postRequest - 1:1 문의를 등록하기 위한 DTO 객체입니다.
     * @since 1.0.0
     */
    void createPost(final PostRequest postRequest, final MemberInfo memberInfo);

    /**
     * 게시글 전체 목록을 조회하는 메소드입니다.
     *
     * @param categoryCode - 페이징 처리를 위한 객체입니다.
     * @param page         - 조회 할 페이지 번호입니다.
     * @param memberInfo   - 등록을 진행할 회원정보입니다.
     * @return 1:1 문의 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<PostResponse> retrievePostList(final String categoryCode, final Integer page, final MemberInfo memberInfo);

    /**
     * 지정한 게시글의 상세정보를 반환하는 메소드입니다.
     *
     * @param postNo     - 지정한 게시글의 식별번호입니다.
     * @param memberInfo - 등록을 진행할 회원정보입니다.
     * @return 지정한 게시글의 상세정보를 반환합니다.
     * @since 1.0.0
     */
    PostResponseForDetail retrievePost(final Long postNo, final MemberInfo memberInfo) throws JsonProcessingException;

    /**
     * 게시판 타입별로 검색을 진행 할 수 있습니다.
     *
     * @param categoryCode  - 검색을 진행 할 게시판 타입입니다.
     * @param searchRequest - 검색을 진행 할 검색 정보입니다.
     * @param memberInfo    - 등록을 진행할 회원정보입니다.
     * @return 검색정보로 검색한 결과목록을 반환합니다.
     * @throws ParseException          파싱도중 예외처리입니다.
     * @throws JsonProcessingException JSON 관련 파싱처리 도중 예외처리입니다.
     * @since 1.0.0
     */
    List<PostResponse> searchForCategory(final String categoryCode, final SearchRequest searchRequest,
                                         final MemberInfo memberInfo)
            throws ParseException, JsonProcessingException;

    /**
     * Reason, Status 와 같은 옵션으로 검색을 진행 할 수 있습니다.
     *
     * @param categoryCode  - 검색을 진행 할 게시판 타입입니다.
     * @param searchRequest - 검색을 진행할 검색 정보입니다.
     * @param optionType    - 게시글을 진행 할 필터 타입입니다.
     * @param option        - 검색을 진행 할 필터 값입니다.
     * @return 검색정보로 검색한 결과목록을 반환합니다.
     * @throws ParseException          파싱도중 예외처리입니다.
     * @throws JsonProcessingException JSON 관련 파싱처리 도중 예외처리입니다.
     * @since 1.0.0
     */
    List<PostResponse> searchForOption(final String categoryCode, final SearchRequest searchRequest,
                                       final String optionType, final String option)
            throws JsonProcessingException, ParseException;

    /**
     * 입력받은 정보로 게시글을 수정 할 수 있습니다.
     *
     * @param categoryCode - 수정할 게시글의 게시판 타입입니다.
     * @param postNo       - 수정할 게시글의 식별번호입니다.
     * @param postRequest  - 수정할 게시글의 정보를 담은 객체입니다.
     * @since 1.0.0
     */
    void updatePost(final String categoryCode, final Long postNo, final PostRequest postRequest);

    /**
     * 1:1 문의의 상태를 변경하는 메소드입니다.
     *
     * @param postNo              - 상태를 변경할 1:1 문의의 식별번호입니다.
     * @param statusUpdateRequest - 변경할 상태 정보를 담고 있는 DTO 객체입니다.
     * @since 1.0.0
     */
    void updateOtoInquiryStatus(final Long postNo, final PostStatusUpdateRequest statusUpdateRequest);

    /**
     * 고객센터 게시글을 삭제하는 메소드입니다.
     *
     * @param categoryCode - 삭제를 진행 할 게시판 타입입니다.
     * @param postNo       - 선택한 1:1 문의의 식별번호입니다.
     * @param memberInfo   - 삭제를 진행 할 회원의 정보입니다.
     * @since 1.0.0
     */
    void deletePost(final String categoryCode, final Long postNo, final MemberInfo memberInfo);

}
