package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostDto;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * 사용자의 고객센터에 관련된 Rest Controller 입니다.
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/shop/v1/customer-services")
@RequiredArgsConstructor
public class CustomerServicePostController {

    private final CustomerServicePostService customerServicePostService;

    private static final String DEFAULT_CUSTOMER_SERVICE = "/shop/v1/customer-services";

    /**
     * 1:1 문의를 등록하는 POST Mapping 을 지원합니다.
     *
     * @param memberId - 1:1 문의를 등록하는 회원의 식별번호입니다.
     * @param customerServicePostDto - 1:1 문의를 등록하기 위한 DTO 객체입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping("/oto-inquiries/members/{memberId}")
    public ResponseEntity<Void> createOtoInquiry(@PathVariable final Long memberId,
                                                 @Valid @RequestBody final CustomerServicePostDto customerServicePostDto) {
        customerServicePostService.createOtoInquiry(memberId, customerServicePostDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_CUSTOMER_SERVICE + "/oto-inquiries/members/" + memberId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 선택한 1:1 문의를 조회하는 GET Mapping 을 지원합니다.
     *
     * @param inquiryId - 선택한 1:1 문의의 식별번호입니다.
     * @return 조회한 1:1 문의 단건의 정보를 담은 객체를 반환합니다.
     * @since 1.0.
     */
    @GetMapping("/oto-inquiries/{inquiryId}")
    public ResponseEntity<CustomerServicePostDto> retrieveOwnOtoInquiry(@PathVariable final Long inquiryId) {
        CustomerServicePostDto inquiryResponse = customerServicePostService.retrieveCustomerServicePost(inquiryId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_CUSTOMER_SERVICE + "/oto-inquiries/" + inquiryId))
                             .body(inquiryResponse);
    }

    /**
     * 회원의 모든 1:1 문의 목록을 조회하는 GET Mapping 을 지원합니다.
     *
     * @param memberId - 조회하는 회원의 식별번호입니다.
     * @param pageable - 페이징 처리를 위한 객체입니다.
     * @return 회원의 1:1 문의 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/oto-inquiries/members/{memberId}")
    public ResponseEntity<List<CustomerServicePostDto>> retrieveOwnOtoInquiries(@PathVariable final Long memberId,
                                                                                final Pageable pageable) {
        List<CustomerServicePostDto> inquiryResponses = customerServicePostService.retrieveOwnOtoInquiries(pageable,
                                                                                                           memberId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_CUSTOMER_SERVICE + "/oto-inquiries/members/" + memberId))
                             .body(inquiryResponses);
    }

    /**
     * 선택한 1:1 문의를 삭제하는 DELETE Mapping 을 지원합니다.
     *
     * @param inquiryId - 선택한 1:1 문의의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/oto-inquiries/{inquiryId}")
    public ResponseEntity<Void> deleteOtoInquiry(@PathVariable final Long inquiryId) {
        customerServicePostService.deleteCustomerServicePost(inquiryId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(
                                     DEFAULT_CUSTOMER_SERVICE + "/oto-inquiries/" + inquiryId))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
