package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.response.CustomerServiceCommentDto;
import com.nhnacademy.marketgg.server.service.CustomerServiceCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * 1:1 문의의 댓글에 관련된 Rest Controller 입니다.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/shop/v1/customer-services/oto-inquiries")
@RequiredArgsConstructor
public class CustomerServiceCommentController {

    private final CustomerServiceCommentService customerServiceCommentService;

    private static final String DEFAULT_CS_COMMENT = "/shop/v1/customer-services/oto-inquiries";

    /**
     * 한 1:1 문의에 대해 댓글을 등록하는 POST Mapping 을 지원합니다.
     *
     * @param inquiryId - 댓글을 등록할 1:1 문의의 식별번호입니다.
     * @param memberId - 댓글을 등록하는 회원의 식별번호입니다.
     * @param customerServiceCommentDto - 댓글을 등록하기 위한 DTO 객체입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/{inquiryId}/members/{memberId}/comments")
    public ResponseEntity<Void> createComment(@PathVariable final Long inquiryId,
                                              @PathVariable final Long memberId,
                                              @RequestBody final CustomerServiceCommentDto customerServiceCommentDto) {
        customerServiceCommentService.createComment(inquiryId, memberId, customerServiceCommentDto);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_CS_COMMENT + "/" + inquiryId + "/members/" + memberId + "/comments"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 댓글 단건을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param commentId - 조회할 댓글의 식별번호입니다.
     * @return 조회한 댓글의 정보를 담은 객체를 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CustomerServiceCommentDto> retrieveComment(@PathVariable final Long commentId) {
        CustomerServiceCommentDto commentResponse = customerServiceCommentService.retrieveComment(commentId);

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create(DEFAULT_CS_COMMENT + "/comments/" + commentId))
                .body(commentResponse);
    }

    /**
     * 한 고객센터 게시글의 댓글 목록을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param inquiryId -게시글의 식별번호입니다.
     * @return 게시글의 댓글 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/{inquiryId}/comments")
    public ResponseEntity<List<CustomerServiceCommentDto>> retrieveInquiryComments(@PathVariable final Long inquiryId) {
        List<CustomerServiceCommentDto> commentResponses = customerServiceCommentService.retrieveCommentsByInquiry(inquiryId);

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create(DEFAULT_CS_COMMENT + "/" + inquiryId + "/comments"))
                .body(commentResponses);
    }

}
