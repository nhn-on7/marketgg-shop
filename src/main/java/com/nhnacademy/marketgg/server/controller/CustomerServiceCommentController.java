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

@RestController
@RequestMapping("/shop/v1/customer-services/oto-inquiries")
@RequiredArgsConstructor
public class CustomerServiceCommentController {

    private final CustomerServiceCommentService customerServiceCommentService;

    private static final String DEFAULT_CS_COMMENT = "/shop/v1/customer-services/oto-inquiries";

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
