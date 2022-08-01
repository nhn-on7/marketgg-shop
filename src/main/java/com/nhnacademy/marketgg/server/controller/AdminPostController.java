package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForDetail;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * 관리자의 고객센터 관리에 관련된 Rest Controller 입니다.
 *
 * @version 1.0.0
 */
@RoleCheck(accessLevel = Role.ROLE_ADMIN)
@RestController
@RequestMapping("/admin/customer-services")
@RequiredArgsConstructor
public class AdminPostController {

    private final CustomerServicePostService postService;

    private static final String DEFAULT_ADMIN_POST = "/admin/customer-services";

    /**
     * 게시글을 등록 할 수 있는 POST Mapping 을 지원합니다.
     *
     * @param postRequest - 등록할 게시글의 정보를 담은 객체입니다.
     * @param memberInfo  - 게시글을 등록할 회원의 정보를 담은 객체입니다.
     * @return Mapping 정보를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody final PostRequest postRequest, final MemberInfo memberInfo) {
        postService.createPost(memberInfo.getId(), postRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_ADMIN_POST))
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
                             .location(URI.create(DEFAULT_ADMIN_POST + "/" + boardNo))
                             .body(response);
    }

    /**
     * 선택한 게시글을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param boardNo - 조회할 게시글의 식별번호입니다.
     * @return 조회할 게시글의 정보를 담은 객체를 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/oto-inquiries/{boardNo}")
    public ResponseEntity<PostResponseForOtoInquiry> retrieveOtoInquiryPost(@PathVariable final Long boardNo) {
        PostResponseForOtoInquiry inquiryResponse = postService.retrieveOtoInquiryPost(boardNo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_POST + "/" + boardNo))
                             .body(inquiryResponse);
    }

    /**
     * 선택한 카테고리 번호의 모든 게시글 목록을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param categoryCode - 조회할 게시글의 게시판 타입입니다.
     * @param page         - 조회 할 페이지 번호입니다.
     * @return 게시글 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}")
    public ResponseEntity<List<PostResponse>> retrievePostList(@PathVariable final String categoryCode,
                                                               @RequestParam final Integer page) {

        List<PostResponse> responses = postService.retrievePostList(categoryCode, page);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_POST + "/oto-inquiries"))
                             .body(responses);
    }

    /**
     * 입력받은 정보로 지정한 게시글을 수정 할 수 있는 PUT Mapping 을 지원합니다.
     *
     * @param boardNo     - 수정할 게시글의 식별번호입니다.
     * @param postRequest - 수정할 게시글의 정보를 담은 객체입니다.
     * @param memberInfo  - 멤버 정보를 담은 객체입니다.
     * @return Mapping URI 를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @PutMapping("/{boardNo}")
    public ResponseEntity<Void> updatePost(@PathVariable final Long boardNo, @RequestBody final PostRequest postRequest,
                                           final MemberInfo memberInfo) {

        postService.updatePost(memberInfo.getId(), boardNo, postRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_POST))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 1:1 문의의 답변 상태를 변경할 수 있는 PATCH Mapping 을 지원합니다.
     *
     * @param boardNo - 상태를 변경할 게시글의 식별번호입니다.
     * @param status  - 변경할 상태 정보를 담고 있는 DTO 객체입니다.
     * @return Mapping URI 를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @PatchMapping("/oto-inquiries/{boardNo}")
    public ResponseEntity<PostResponseForOtoInquiry> updateInquiryStatus(@PathVariable final Long boardNo,
                                                                         @RequestBody final PostStatusUpdateRequest status) {

        postService.updateInquiryStatus(boardNo, status);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_POST + "/oto-inquiries/" + boardNo))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 선택한 1:1 문의를 삭제하는 DELETE Mapping 을 지원합니다.
     *
     * @param boardNo - 삭제할 1:1 문의의 식별번호입니다.
     * @return Mapping URI 를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/{boardNo}")
    public ResponseEntity<Void> deletePost(@PathVariable final Long boardNo) {
        postService.deletePost(boardNo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_POST + "/" + boardNo))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
