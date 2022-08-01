package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.constant.CustomerServicePostReason;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import com.nhnacademy.marketgg.server.dto.response.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForDetail;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.dto.response.SearchBoardResponse;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 사용자의 고객센터에 관련된 Rest Controller 입니다.
 *
 * @version 1.0.0
 */
@RoleCheck(accessLevel = Role.ROLE_USER)
@RestController
@RequestMapping("/customer-services")
@RequiredArgsConstructor
public class PostController {

    private final CustomerServicePostService postService;

    private static final String DEFAULT_POST = "/customer-services";

    /**
     * 1:1 문의를 등록하는 POST Mapping 을 지원합니다.
     * 1:1 문의 = one-to-one inquiry
     * one-to-one 을 축약하여 oto 으로 표현하였습니다.
     *
     * @param memberInfo  - 1:1 문의를 등록하는 회원의 정보입니다.
     * @param postRequest - 1:1 문의를 등록하기 위한 CustomerServicePostDto 객체입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/oto-inquiries")
    public ResponseEntity<Void> createOtoInquiry(@Valid @RequestBody final PostRequest postRequest,
                                                 final MemberInfo memberInfo) {

        postService.createPost(memberInfo.getId(), postRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(
                                     DEFAULT_POST + "/oto-inquiries"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 지정한 게시글의 상세정보를 조회할 수 있는 GET Mapping 을 지원합니다.
     *
     * @param boardNo - 조회할 게시글의 식별번호입니다.
     * @return 지정한 게시글의 상세 정보를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/{boardNo}")
    public ResponseEntity<PostResponseForDetail> retrievePost(@PathVariable final Long boardNo) {
        PostResponseForDetail response = postService.retrievePost(boardNo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_POST + "/" + boardNo))
                             .body(response);
    }

    /**
     * 선택한 게시글을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param boardNo - 선택한 게시글의 식별번호입니다.
     * @return 조회한 1:1 문의 단건의 정보를 담은 객체를 반환합니다.
     * @since 1.0.
     */
    @GetMapping("/oto-inquiries/{boardNo}")
    public ResponseEntity<PostResponseForOtoInquiry> retrieveOwnOtoInquiry(@PathVariable final Long boardNo) {
        PostResponseForOtoInquiry inquiryResponse = postService.retrieveOtoInquiryPost(boardNo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_POST + "/oto-inquiries/" + boardNo))
                             .body(inquiryResponse);
    }

    /**
     * 회원의 모든 고객센터 게시글 목록을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param memberInfo - 조회하는 회원의 정보입니다.
     * @param page       - 페이징 처리를 위한 페이지 번호입니다..
     * @return 회원의 1:1 문의 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}")
    public ResponseEntity<List<PostResponse>> retrievesPostList(@PathVariable final String categoryCode,
                                                                final MemberInfo memberInfo, final Integer page) {

        List<PostResponse> responses = postService.retrievesOwnPostList(page, categoryCode, memberInfo.getId());

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_POST + "/categories/" + categoryCode))
                             .body(responses);
    }

    /**
     * 지정한 게시판 타입의 전체 목록을 검색한 결과를 반환합니다.
     *
     * @param categoryCode  - 검색을 진행 할 게시판 타입입니다.
     * @param searchRequest - 검색을 진행 할 검색 정보입니다.
     * @return 검색정보로 검색한 결과 목록 응답객체를 반환합니다.
     * @throws ParseException          파싱도중 예외처리입니다.
     * @throws JsonProcessingException JSON 관련 파싱처리 도중 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/categories/{categoryCode}/search")
    public ResponseEntity<List<SearchBoardResponse>> searchPostListForCategory(@PathVariable final String categoryCode,
                                                                               @RequestBody final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException {

        List<SearchBoardResponse> responses = postService.searchForCategory(categoryCode, searchRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_POST + "/categories/" + categoryCode + "/search"))
                             .body(responses);
    }

    /**
     * 지정한 게시판 타입의 Reason 옵션으로 검색한 결과를 반환합니다.
     *
     * @param categoryCode  - 검색을 진행 할 게시판 타입입니다.
     * @param option        - 검색을 진행 할 필터의 값입니다.
     * @param searchRequest - 검색을 진행 할 검색정보입니다.
     * @return 검색정보로 검색한 결과 목록 응답객체를 반환합니다.
     * @throws ParseException          파싱도중 예외처리입니다.
     * @throws JsonProcessingException JSON 관련 파싱처리 도중 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/categories/{categoryCode}/search/reason/{option}")
    public ResponseEntity<List<SearchBoardResponse>> searchPostListForReason(@PathVariable final String categoryCode,
                                                                             @PathVariable final String option,
                                                                             @RequestBody final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException {

        List<SearchBoardResponse> responses =
                postService.searchForOption(categoryCode, searchRequest, option, "reason");

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                     DEFAULT_POST + "/categories/" + categoryCode + "/search/reason/" + option))
                             .body(responses);
    }

    /**
     * 지정한 게시판 타입의 Status 옵션으로 검색한 결과를 반환합니다.
     *
     * @param categoryCode  - 검색을 진행 할 게시판 타입입니다.
     * @param option        - 검색을 진행 할 필터의 값입니다.
     * @param searchRequest - 검색을 진행 할 검색정보입니다.
     * @return 검색정보로 검색한 결과 목록 응답객체를 반환합니다.
     * @throws ParseException          파싱도중 예외처리입니다.
     * @throws JsonProcessingException JSON 관련 파싱처리 도중 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/categories/{categoryCode}/search/status/{option}")
    public ResponseEntity<List<SearchBoardResponse>> searchPostListForStatus(@PathVariable final String categoryCode,
                                                                             @PathVariable final String option,
                                                                             @RequestBody final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException {

        List<SearchBoardResponse> responses =
                postService.searchForOption(categoryCode, searchRequest, option, "status");

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                     DEFAULT_POST + "/categories/" + categoryCode + "/search/status/" + option))
                             .body(responses);
    }

    /**
     * 선택한 1:1 문의를 삭제하는 DELETE Mapping 을 지원합니다.
     *
     * @param boardNo - 선택한 1:1 문의의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/oto-inquiries/{boardNo}")
    public ResponseEntity<Void> deleteOtoInquiry(@PathVariable final Long boardNo) {
        postService.deletePost(boardNo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_POST + "/oto-inquiries/" + boardNo))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 고객센터 게시글의 사유 목록을 불러오는 GET Mapping 을 지원합니다.
     *
     * @return 사유 목록을 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/reasons")
    public ResponseEntity<List<String>> retrieveAllReasonValues() {
        List<String> reasons = Arrays.stream(CustomerServicePostReason.values())
                                     .map(CustomerServicePostReason::reason)
                                     .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_POST + "/reasons"))
                             .body(reasons);
    }

}
