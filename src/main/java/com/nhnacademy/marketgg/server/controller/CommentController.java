package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.customerservice.CommentRequest;
import com.nhnacademy.marketgg.server.service.OtoInquiryCommentService;
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
 * @author 박세완
 * @version 1.0.0
 */
@RoleCheck(accessLevel = Role.ROLE_USER)
@RestController
@RequestMapping("/customer-services")
@RequiredArgsConstructor
public class CommentController {

    private static final String DEFAULT_CS = "/customer-services";
    private final OtoInquiryCommentService otoInquiryCommentService;

    /**
     * 한 1:1 문의에 대해 댓글을 등록하는 POST Mapping 을 지원합니다.
     *
     * @param postNo      - 댓글을 등록할 1:1 문의의 식별번호입니다.
     * @param memberInfo     - 댓글을 등록하는 회원의 정보입니다.
     * @param commentRequest - 댓글을 등록하기 위한 DTO 객체입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/{postNo}")
    public ResponseEntity<Void> createComment(@PathVariable final Long postNo,
                                              @RequestBody final CommentRequest commentRequest,
                                              final MemberInfo memberInfo) {

        otoInquiryCommentService.createComment(postNo, memberInfo.getId(), commentRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_CS + "/" + postNo))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
