package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.CommentRequest;
import com.nhnacademy.marketgg.server.service.CustomerServiceCommentService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1:1 문의의 댓글에 관련된 Rest Controller 입니다.
 *
 * @version 1.0.0
 */
// @RoleCheck(accessLevel = Role.ROLE_USER)
@RestController
@RequestMapping("/customer-services/oto-inquiries")
@RequiredArgsConstructor
public class CustomerServiceCommentController {

    private final CustomerServiceCommentService customerServiceCommentService;

    private static final String DEFAULT_CS_COMMENT = "/customer-services/oto-inquiries";

    /**
     * 한 1:1 문의에 대해 댓글을 등록하는 POST Mapping 을 지원합니다.
     *
     * @param inquiryId      - 댓글을 등록할 1:1 문의의 식별번호입니다.
     * @param memberId       - 댓글을 등록하는 회원의 식별번호입니다.
     * @param commentRequest - 댓글을 등록하기 위한 DTO 객체입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/{inquiryId}/members/{memberId}/comments")
    public ResponseEntity<Void> createComment(@PathVariable final Long inquiryId,
                                              @PathVariable final Long memberId,
                                              @RequestBody final CommentRequest commentRequest) {
        customerServiceCommentService.createComment(inquiryId, memberId, commentRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(
                                 DEFAULT_CS_COMMENT + "/" + inquiryId + "/members/" + memberId + "/comments"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
