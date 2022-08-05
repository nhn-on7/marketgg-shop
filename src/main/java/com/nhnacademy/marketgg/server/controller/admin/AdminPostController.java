package com.nhnacademy.marketgg.server.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.constant.OtoStatus;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostRequest;
import com.nhnacademy.marketgg.server.dto.request.customerservice.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.service.PostService;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @author 박세완, 김정민
 * @version 1.0.0
 */
@RoleCheck(accessLevel = Role.ROLE_ADMIN)
@RestController
@RequestMapping("/admin/customer-services")
@RequiredArgsConstructor
public class AdminPostController {

    private final PostService postService;

    private static final String DEFAULT_ADMIN_POST = "/admin/customer-services";

    /**
     * 지정한 게시판 타입의 Reason 옵션으로 검색한 결과를 반환합니다.
     *
     * @param categoryCode  - 검색을 진행 할 게시판 타입입니다.
     * @param option        - 검색을 진행 할 필터의 값입니다.
     * @param searchRequest - 검색을 진행 할 검색정보입니다.
     * @return 검색정보로 검색한 결과 목록 응답객체를 반환합니다.
     * @throws ParseException          파싱도중 예외처리입니다.
     * @throws JsonProcessingException JSON 관련 파싱처리 도중 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/categories/{categoryCode}/options/{optionType}/search")
    public ResponseEntity<List<PostResponse>> searchPostListForOption(@PathVariable final String categoryCode,
                                                                      @PathVariable final String optionType,
                                                                      @RequestParam final String option,
                                                                      @RequestBody final SearchRequest searchRequest)
            throws ParseException, JsonProcessingException {

        List<PostResponse> responses =
                postService.searchForOption(categoryCode, searchRequest, optionType, option);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                     DEFAULT_ADMIN_POST + "/categories/" + categoryCode + "/options/" + optionType
                                             + "/search?option=" + option))
                             .body(responses);
    }

    /**
     * 입력받은 정보로 지정한 게시글을 수정 할 수 있는 PUT Mapping 을 지원합니다.
     *
     * @param categoryCode - 수정할 게시글의 카테고리 번호입니다.
     * @param postNo       - 수정할 게시글의 식별번호입니다.
     * @param postRequest  - 수정할 게시글의 정보를 담은 객체입니다.
     * @return Mapping URI 를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @PutMapping("/categories/{categoryCode}/{postNo}")
    public ResponseEntity<Void> updatePost(@PathVariable final String categoryCode, @PathVariable final Long postNo,
                                           @RequestBody final PostRequest postRequest) {

        postService.updatePost(categoryCode, postNo, postRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_POST + "/categories/" + categoryCode + "/" + postNo))
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
     * @param postNo              - 상태를 변경할 게시글의 식별번호입니다.
     * @param statusUpdateRequest - 변경할 상태 정보를 담고 있는 DTO 객체입니다.
     * @return Mapping URI 를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @PatchMapping("/{postNo}/status")
    public ResponseEntity<Void> updateInquiryStatus(@PathVariable final Long postNo,
                                                    @RequestBody final PostStatusUpdateRequest statusUpdateRequest) {

        postService.updateOtoInquiryStatus(postNo, statusUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_POST + "/" + postNo + "/status"))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
