package com.nhnacademy.marketgg.server.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.constant.OtoStatus;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.service.post.PostService;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private static final Integer PAGE_SIZE = 10;

    /**
     * 지정한 게시판 타입의 Reason 옵션으로 검색한 결과를 반환합니다.
     *
     * @param categoryId - 검색을 진행 할 게시판 타입입니다.
     * @param option     - 검색을 진행 할 필터의 값입니다.
     * @param optionType - 검색을 진행 할 옵션 타입입니다.
     * @param keyword    - 검색을 진행 할 검색정보입니다.
     * @param page       - 검색을 진행 할 페이지 정보입니다.
     * @return 검색정보로 검색한 결과 목록 응답객체를 반환합니다.
     * @throws ParseException          파싱도중 예외처리입니다.
     * @throws JsonProcessingException JSON 관련 파싱처리 도중 예외처리입니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryId}/options/{optionType}/search")
    public ResponseEntity<List<PostResponse>> searchPostListForOption(
        @PathVariable @Size(min = 1, max = 6) final String categoryId,
        @PathVariable @Min(1) final String optionType,
        @RequestParam @Min(1) final String option,
        @RequestParam @Size(min = 1, max = 30) final String keyword,
        @RequestParam @Min(0) final Integer page)
        throws ParseException, JsonProcessingException {

        List<PostResponse> responses =
            postService.searchForOption(categoryId, new SearchRequest(keyword, page, PAGE_SIZE), optionType,
                option);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                 DEFAULT_ADMIN_POST + "/categories/" + categoryId + "/options/" + optionType
                                     + "/search?option=" + option))
                             .body(responses);
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
    @PutMapping("/categories/{categoryId}/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable @Size(min = 1, max = 6) final String categoryId,
                                           @PathVariable @Min(1) final Long postId,
                                           @Valid @RequestBody final PostRequest postRequest) {

        postService.updatePost(categoryId, postId, postRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_POST + "/categories/" + categoryId + "/" + postId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 1:1 문의의 상태목록을 반환하는 GET Mapping 을 지원합니다.
     *
     * @return 1:1 문의의 상태목록을 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/status")
    public ResponseEntity<List<String>> retrieveStatusList() {
        List<String> status = Arrays.stream(OtoStatus.values())
                                    .map(OtoStatus::status)
                                    .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_POST + "/status"))
                             .body(status);
    }

    /**
     * 1:1 문의의 답변 상태를 변경할 수 있는 PATCH Mapping 을 지원합니다.
     *
     * @param postId              - 상태를 변경할 게시글의 식별번호입니다.
     * @param statusUpdateRequest - 변경할 상태 정보를 담고 있는 DTO 객체입니다.
     * @return Mapping URI 를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @PatchMapping("/{postId}/status")
    public ResponseEntity<Void> updateInquiryStatus(@PathVariable @Min(1) final Long postId,
                                                    @Valid @RequestBody
                                                    final PostStatusUpdateRequest statusUpdateRequest) {

        postService.updateOtoInquiryStatus(postId, statusUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_POST + "/" + postId + "/status"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
