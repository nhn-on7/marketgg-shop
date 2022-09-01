package com.nhnacademy.marketgg.server.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.server.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자의 고객센터 관리에 관련된 Rest Controller 입니다.
 *
 * @author 김정민, 박세완
 * @version 1.0.0
 */
@RestController
@RequestMapping("/admin/customer-services")
@RequiredArgsConstructor
public class AdminCsPostController {

    private final PostService postService;

    private static final String DEFAULT_ADMIN_POST = "/admin/customer-services";

    /**
     * 지정한 게시판 타입의 지정한 옵션으로 검색한 결과를 반환합니다.
     *
     * @param option        - 검색을 진행 할 필터의 값입니다.
     * @param optionType    - 검색을 진행 할 옵션 타입입니다.
     * @param searchRequest - 검색을 진행할 정보입니다.
     * @return 검색정보로 검색한 결과 목록 응답객체를 반환합니다.
     * @throws ParseException          파싱도중 예외처리입니다.
     * @throws JsonProcessingException JSON 관련 파싱처리 도중 예외처리입니다.
     * @since 1.0.0
     */
    @Operation(summary = "옵션에 따른 게시글 목록검색",
        description = "지정한 카테고리 번호로 지정한 사유 또는 상태에 따른 검색을 진행합니다.",
        parameters = { @Parameter(name = "categoryId", description = "카테고리 식별번호", required = true),
            @Parameter(name = "optionType", description = "사유 또는 상태", required = true),
            @Parameter(name = "option", description = "지정한 옵션의 값", required = true),
            @Parameter(name = "searchRequest", description = "검색 옵션 지정", required = true) },
        responses = @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ShopResult.class)),
            useReturnTypeSchema = true))
    @PostMapping("/categories/{categoryId}/options/{optionType}/search")
    public ResponseEntity<ShopResult<List<PostResponse>>> searchPostListForOption(
        @PathVariable @Size(min = 1, max = 6) final String categoryId,
        @PathVariable @Min(1) final String optionType,
        @RequestParam @Min(1) final String option,
        @Valid @RequestBody final SearchRequest searchRequest)
        throws ParseException, JsonProcessingException {
        List<PostResponse> data = postService.searchForOption(searchRequest, optionType, option);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                 DEFAULT_ADMIN_POST + "/categories/" + categoryId + "/options/" + optionType
                                     + "/search?option=" + option))
                             .body(ShopResult.successWith(data));
    }

    /**
     * 입력받은 정보로 지정한 게시글을 수정 할 수 있는 PUT Mapping 을 지원합니다.
     *
     * @param categoryId  - 수정할 게시글의 카테고리 번호입니다.
     * @param postId      - 수정할 게시글의 식별번호입니다.
     * @param postRequest - 수정할 게시글의 정보를 담은 객체입니다.
     * @return Mapping URI 를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @Operation(summary = "게시글 수정",
        description = "지정한 게시글을 지정한 수정 값에 따라 수정합니다.",
        parameters = { @Parameter(name = "categoryId", description = "카테고리 식별번호", required = true),
            @Parameter(name = "postId", description = "게시글 식별번호", required = true),
            @Parameter(name = "postRequest", description = "수정 할 값 지정", required = true) },
        responses = @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ShopResult.class)),
            useReturnTypeSchema = true))
    @PutMapping("/categories/{categoryId}/{postId}")
    public ResponseEntity<ShopResult<String>> updatePost(@PathVariable @Size(min = 1, max = 6) final String categoryId,
                                                         @PathVariable @Min(1) final Long postId,
                                                         @Valid @RequestBody final PostRequest postRequest) {

        postService.updatePost(categoryId, postId, postRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_POST + "/categories/" + categoryId + "/" + postId))
                             .body(ShopResult.successWithDefaultMessage());
    }

    /**
     * 1:1 문의의 답변 상태를 변경할 수 있는 PATCH Mapping 을 지원합니다.
     *
     * @param postId              - 상태를 변경할 게시글의 식별번호입니다.
     * @param statusUpdateRequest - 변경할 상태 정보를 담고 있는 DTO 객체입니다.
     * @return Mapping URI 를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @Operation(summary = "1:1문의 답변상태 변경",
        description = "지정한 1:1문의 게시글의 지정한 상태로 답변 상태를 변경합니다.",
        parameters = { @Parameter(name = "postId", description = "게시글 식별번호", required = true),
            @Parameter(name = "statusUpdateRequest", description = "상태 값", required = true) },
        responses = @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ShopResult.class)),
            useReturnTypeSchema = true))
    @PatchMapping("/{postId}/status")
    public ResponseEntity<ShopResult<String>> updateInquiryStatus(@PathVariable @Min(1) final Long postId,
                                                                  @Valid @RequestBody
                                                                  final PostStatusUpdateRequest statusUpdateRequest) {

        postService.updateOtoInquiryStatus(postId, statusUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_POST + "/" + postId + "/status"))
                             .body(ShopResult.successWithDefaultMessage());
    }

}
