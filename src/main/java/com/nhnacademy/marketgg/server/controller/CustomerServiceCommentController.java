package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.response.CustomerServiceCommentDto;
import com.nhnacademy.marketgg.server.service.CustomerServiceCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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

}
