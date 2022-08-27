package com.nhnacademy.marketgg.server.controller.customerservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.constant.OtoReason;
import com.nhnacademy.marketgg.server.constant.OtoStatus;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostRequest;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForDetail;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자의 고객센터에 관련된 Rest Controller 입니다.
 *
 * @author 박세완, 김정민
 * @version 1.0.0
 */
@RestController
@RequestMapping("/customer-services")
@RequiredArgsConstructor
public class CsPostController {

    private final PostService postService;

    private static final String DEFAULT_POST = "/customer-services";

    /**
     * 게시글을 등록하는 POST Mapping 을 지원합니다.
     *
     * @param memberInfo  - 게시글을 등록하는 회원의 정보입니다.
     * @param postRequest - 게시글을 등록하기 위한 PostRequest 객체입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @Operation(summary = "게시글 등록",
               description = "입력한 정보를 기반으로 게시글을 등록합니다.",
               parameters = { @Parameter(name = "postRequest", description = "등록할 게시글 정보", required = true),
                       @Parameter(name = "memberInfo", description = "회원 정보", required = true) },
               responses = @ApiResponse(responseCode = "201",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @PostMapping
    public ResponseEntity<ShopResult<String>> createPost(@Valid @RequestBody final PostRequest postRequest,
                                                         final MemberInfo memberInfo) {

        postService.createPost(postRequest, memberInfo);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_POST))
                             .body(ShopResult.successWithDefaultMessage());
    }

    /**
     * 카테고리에 따라 고객센터 게시글 목록을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param categoryId - 조회할 게시글 목록의 카테고리 식별번호입니다.
     * @param page       - 페이징 처리를 위한 페이지 번호입니다.
     * @param memberInfo - 로그인 한 회원의 정보입니다.
     * @return 게시글 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @Operation(summary = "게시글 목록조회",
               description = "지정한 카테고리 번호의 게시글 목록을 조회합니다.",
               parameters = { @Parameter(name = "categoryId", description = "카테고리 식별번호", required = true),
                       @Parameter(name = "page", description = "페이지 번호", required = true),
                       @Parameter(name = "memberInfo", description = "회원 정보", required = true) },
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ShopResult<List<PostResponse>>> retrievePostList(
            @PathVariable @NotBlank @Size(min = 1, max = 6) final String categoryId,
            @RequestParam @NotNull final Integer page,
            final MemberInfo memberInfo) {

        List<PostResponse> data = postService.retrievePostList(categoryId, page, memberInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_POST + "/categories/" + categoryId))
                             .body(ShopResult.successWith(data));
    }

    /**
     * 지정한 게시글의 상세정보를 조회할 수 있는 GET Mapping 을 지원합니다.
     *
     * @param postId     - 조회할 게시글의 식별번호입니다.
     * @param memberInfo - 로그인 한 회원의 정보입니다.
     * @return 지정한 게시글의 상세 정보를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @Operation(summary = "게시글 상세조회",
               description = "지정한 게시글의 상세정보를 조회합니다.",
               parameters = { @Parameter(name = "postId", description = "게시글 식별번호", required = true),
                       @Parameter(name = "memberInfo", description = "회원 정보", required = true) },
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @GetMapping("/{postId}")
    public ResponseEntity<ShopResult<PostResponseForDetail>> retrievePost(
            @PathVariable @NotNull @Min(1) final Long postId,
            final MemberInfo memberInfo)
            throws JsonProcessingException {

        PostResponseForDetail data = postService.retrievePost(postId, memberInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_POST + "/" + postId))
                             .body(ShopResult.successWith(data));
    }

    /**
     * 지정한 게시판 타입의 전체 목록에서 검색한 결과를 반환합니다.
     *
     * @param categoryId    - 검색을 진행 할 게시판 타입입니다.
     * @param searchRequest - 검색을 진행할 정보입니다.
     * @param memberInfo    - 검색을 진행 할 회원의 정보입니다.
     * @return 검색정보로 검색한 결과 목록 응답객체를 반환합니다.
     * @throws ParseException          파싱도중 예외처리입니다.
     * @throws JsonProcessingException JSON 관련 파싱처리 도중 예외처리입니다.
     * @since 1.0.0
     */
    @Operation(summary = "지정한 카테고리 목록 내 게시글 검색",
               description = "지정한 게시판 타입의 전체 목록에서 검색을 진행합니다.",
               parameters = { @Parameter(name = "categoryId", description = "카테고리 식별번호", required = true),
                       @Parameter(name = "searchRequest", description = "검색 옵션 값", required = true),
                       @Parameter(name = "memberInfo", description = "회원 정보", required = true) },
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @PostMapping("/categories/{categoryId}/search")
    public ResponseEntity<ShopResult<List<PostResponse>>> searchPostListForCategory(
            @PathVariable @Size(min = 1, max = 6) final String categoryId,
            @Valid @RequestBody final SearchRequest searchRequest,
            final MemberInfo memberInfo)
            throws ParseException, JsonProcessingException {

        List<PostResponse> data = postService.searchForCategory(searchRequest, memberInfo);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_POST + "/categories/" + categoryId + "/search"))
                             .body(ShopResult.successWith(data));
    }

    /**
     * 선택한 1:1 문의를 삭제하는 DELETE Mapping 을 지원합니다.
     *
     * @param categoryId - 삭제를 진행 할 게시판 타입입니다.
     * @param postId     - 선택한 1:1 문의의 식별번호입니다.
     * @param memberInfo - 삭제를 진행 할 회원의 정보입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @Operation(summary = "1:1 문의 삭제",
               description = "지정한 1:1문의 게시글을 삭제합니다.",
               parameters = { @Parameter(name = "categoryId", description = "카테고리 식별번호", required = true),
                       @Parameter(name = "postId", description = "게시글 식별번호", required = true),
                       @Parameter(name = "memberInfo", description = "회원 정보", required = true) },
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @DeleteMapping("/categories/{categoryId}/{postId}")
    public ResponseEntity<ShopResult<String>> deletePost(
            @PathVariable @NotBlank @Size(min = 1, max = 6) final String categoryId,
            @PathVariable @NotNull @Min(1) final Long postId,
            final MemberInfo memberInfo) {

        postService.deletePost(categoryId, postId, memberInfo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .location(URI.create(DEFAULT_POST + postId))
                             .body(ShopResult.successWithDefaultMessage());
    }

    /**
     * 고객센터 게시글의 사유 목록을 불러오는 GET Mapping 을 지원합니다.
     *
     * @return 사유 목록을 반환합니다.
     * @since 1.0.0
     */
    @Operation(summary = "1:1 문의 사유목록 반환",
               description = "1:1 문의 사유목록을 반환합니다.",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @GetMapping("/reasons")
    public ResponseEntity<ShopResult<List<String>>> retrieveReasonList() {
        List<String> data = Arrays.stream(OtoReason.values())
                                  .map(OtoReason::reason)
                                  .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_POST + "/reasons"))
                             .body(ShopResult.successWith(data));
    }

    /**
     * 1:1 문의의 상태목록을 반환하는 GET Mapping 을 지원합니다.
     *
     * @return 1:1 문의의 상태목록을 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @Operation(summary = "1:1문의 상태목록 반환",
               description = "1:1 문의의 상태목록을 반환합니다.",
               responses = @ApiResponse(responseCode = "200",
                                        content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = ShopResult.class)),
                                        useReturnTypeSchema = true))
    @GetMapping("/status")
    public ResponseEntity<ShopResult<List<String>>> retrieveStatusList() {
        List<String> data = Arrays.stream(OtoStatus.values())
                                  .map(OtoStatus::status)
                                  .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_POST + "/status"))
                             .body(ShopResult.successWith(data));
    }

}
