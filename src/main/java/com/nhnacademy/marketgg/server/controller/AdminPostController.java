package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 선택한 1:1 문의 단건을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param inquiryId - 조회할 1:1 문의의 식별번호입니다.
     * @return 1:1 문의의 정보를 담은 객체를 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/oto-inquiries/{inquiryId}")
    public ResponseEntity<PostResponseForOtoInquiry> retrieveOtoInquiry(
            @PathVariable final Long inquiryId) {
        PostResponseForOtoInquiry inquiryResponse = postService.retrievePost(inquiryId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(
                                     URI.create(DEFAULT_ADMIN_POST + "/oto-inquiries" + inquiryId))
                             .body(inquiryResponse);
    }

    /**
     * 모든 1:1 문의 목록을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param pageable - 페이징 처리를 위한 객체입니다.
     * @return 1:1 문의 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/oto-inquiries")
    public ResponseEntity<List<PostResponseForOtoInquiry>> retrieveOtoInquiries(
            final Pageable pageable) {
        List<PostResponseForOtoInquiry> inquiryResponses = postService.retrievePostList(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_POST + "/oto-inquiries"))
                             .body(inquiryResponses);
    }

    /**
     * 1:1 문의의 답변 상태를 변경할 수 있는 PATCH Mapping 을 지원합니다.
     *
     * @param inquiryId - 상태를 변경할 게시글의 식별번호입니다.
     * @param status    - 변경할 상태 정보를 담고 있는 DTO 객체입니다.
     * @return Mapping URI 를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @PatchMapping("/oto-inquiries/{inquiryId}")
    public ResponseEntity<PostResponseForOtoInquiry> updateInquiryStatus(
            @PathVariable final Long inquiryId, @RequestBody final PostStatusUpdateRequest status) {

        postService.updateInquiryStatus(inquiryId, status);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(
                                     URI.create(DEFAULT_ADMIN_POST + "/oto-inquiries/" + inquiryId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 선택한 1:1 문의를 삭제하는 DELETE Mapping 을 지원합니다.
     *
     * @param inquiryId - 삭제할 1:1 문의의 식별번호입니다.
     * @return Mapping URI 를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/oto-inquiries/{inquiryId}")
    public ResponseEntity<Void> deleteOtoInquiries(@PathVariable final Long inquiryId) {
        postService.deletePost(inquiryId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(
                                     URI.create(DEFAULT_ADMIN_POST + "/oto-inquiries/" + inquiryId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
