package com.nhnacademy.marketgg.server.controller.customerservice;

import com.nhnacademy.marketgg.server.annotation.Auth;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.customerservice.CommentRequest;
import com.nhnacademy.marketgg.server.service.otoinquiry.OtoInquiryCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.URI;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@Auth
@RestController
@RequestMapping("/customer-services")
@RequiredArgsConstructor
public class CommentController {

    private static final String DEFAULT_CS = "/customer-services";
    private final OtoInquiryCommentService otoInquiryCommentService;

    /**
     * 한 1:1 문의에 대해 댓글을 등록하는 POST Mapping 을 지원합니다.
     *
     * @param postId         - 댓글을 등록할 1:1 문의의 식별번호입니다.
     * @param memberInfo     - 댓글을 등록하는 회원의 정보입니다.
     * @param commentRequest - 댓글을 등록하기 위한 DTO 객체입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @Operation(summary = "1:1문의 댓글 등록",
               description = "지정한 1:1 문의 게시글에 입력한 정보로 댓글을 등록합니다.",
               parameters = { @Parameter(name = "postId", description = "게시글 식별번호", required = true),
                       @Parameter(name = "commentRequest", description = "댓글 정보 입력값", required = true),
                       @Parameter(name = "memberInfo", description = "멤버 정보", required = true) },
               responses = @ApiResponse(responseCode = "201",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @PostMapping("/{postId}")
    public ResponseEntity<ShopResult<String>> createComment(@PathVariable @Min(1) final Long postId,
                                                            @Valid @RequestBody final CommentRequest commentRequest,
                                                            final MemberInfo memberInfo) {

        otoInquiryCommentService.createComment(postId, memberInfo.getId(), commentRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_CS + "/" + postId))
                             .body(ShopResult.successWithDefaultMessage());
    }

}
