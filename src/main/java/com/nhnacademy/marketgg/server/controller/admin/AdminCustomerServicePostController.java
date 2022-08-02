package com.nhnacademy.marketgg.server.controller.admin;

import com.nhnacademy.marketgg.server.constant.CustomerServicePostReason;
import com.nhnacademy.marketgg.server.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자의 고객센터 관리에 관련된 Rest Controller 입니다.
 *
 * @version 1.0.0
 */
// @RoleCheck(accessLevel = Role.ROLE_ADMIN)
@RestController
@RequestMapping("/admin/customer-services")
@RequiredArgsConstructor
public class AdminCustomerServicePostController {

    private final CustomerServicePostService customerServicePostService;

    private static final String ADMIN_CUSTOMER_SERVICE_PATH = "/admin/customer-services";
    private static final String ONE_TO_ONE_INQUIRIES_PATH = "/oto-inquiries/";

    /**
     * 선택한 1:1 문의 단건을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param inquiryId - 조회할 1:1 문의의 식별번호입니다.
     * @return 1:1 문의의 정보를 담은 객체를 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/oto-inquiries/{inquiryId}")
    public ResponseEntity<PostResponseForOtoInquiry> retrieveOtoInquiry(@PathVariable final Long inquiryId) {
        PostResponseForOtoInquiry inquiryResponse = customerServicePostService.retrieveCustomerServicePost(inquiryId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ADMIN_CUSTOMER_SERVICE_PATH + ONE_TO_ONE_INQUIRIES_PATH + inquiryId))
                             .body(inquiryResponse);
    }

    /**
     * 모든 1:1 문의 목록을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param pageable - 페이징 처리를 위한 객체입니다.
     * @return 1:1 문의 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/oto-inquiries")
    public ResponseEntity<List<PostResponseForOtoInquiry>> retrieveOtoInquiries(final Pageable pageable) {
        List<PostResponseForOtoInquiry> inquiryResponses = customerServicePostService.retrieveOtoInquiries(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ADMIN_CUSTOMER_SERVICE_PATH + "/oto-inquiries"))
                             .body(inquiryResponses);
    }

    /**
     * 1:1 문의의 답변 상태를 변경할 수 있는 PATCH Mapping 을 지원합니다.
     *
     * @param inquiryId - 상태를 변경할 게시글의 식별번호입니다.
     * @param status    - 변경할 상태 정보를 담고 있는 DTO 객체입니다.
     * @return Mapping URI 를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @PatchMapping("/oto-inquiries/{inquiryId}")
    public ResponseEntity<PostResponseForOtoInquiry> updateInquiryStatus(@PathVariable final Long inquiryId,
                                                                         @RequestBody
                                                                         final PostStatusUpdateRequest status) {
        customerServicePostService.updateInquiryStatus(inquiryId, status);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ADMIN_CUSTOMER_SERVICE_PATH + ONE_TO_ONE_INQUIRIES_PATH + inquiryId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 선택한 1:1 문의를 삭제하는 DELETE Mapping 을 지원합니다.
     *
     * @param inquiryId - 삭제할 1:1 문의의 식별번호입니다.
     * @return Mapping URI 를 담은 응답객체를 반환합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/oto-inquiries/{inquiryId}")
    public ResponseEntity<Void> deleteOtoInquiries(@PathVariable final Long inquiryId) {
        customerServicePostService.deleteCustomerServicePost(inquiryId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ADMIN_CUSTOMER_SERVICE_PATH + ONE_TO_ONE_INQUIRIES_PATH + inquiryId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 고객센터 게시글의 사유 목록을 불러오는 GET Mapping 을 지원합니다.
     *
     * @return 사유 목록을 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/reasons")
    public ResponseEntity<List<String>> retrieveAllReasonValues() {
        List<String> reasons = Arrays.stream(CustomerServicePostReason.values())
                                     .map(CustomerServicePostReason::reason)
                                     .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(ADMIN_CUSTOMER_SERVICE_PATH + "/reasons"))
                             .body(reasons);
    }

}
