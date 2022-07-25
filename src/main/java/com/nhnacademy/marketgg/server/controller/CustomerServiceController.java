package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostRetrieveResponse;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/shop/v1/customer-services")
@RequiredArgsConstructor
public class CustomerServiceController {

    private final CustomerServicePostService customerServicePostService;

    private static final String DEFAULT_CUSTOMER_SERVICE = "/shop/v1/admin/customer-services";

    @GetMapping("/oto-inquiries/{inquiryId}/members/{memberId}")
    public ResponseEntity<CustomerServicePostRetrieveResponse> retrieveOwnOtoInquiry(@PathVariable final Long inquiryId,
                                                                                     @PathVariable final Long memberId) {
        CustomerServicePostRetrieveResponse inquiryResponse = customerServicePostService.retrieveOwnOtoInquiry(inquiryId, memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create(DEFAULT_CUSTOMER_SERVICE + "/oto-inquiries/" + inquiryId + "/members/" + memberId))
                .body(inquiryResponse);
    }

    @GetMapping("/oto-inquiries/members/{memberId}")
    public ResponseEntity<List<CustomerServicePostRetrieveResponse>> retrieveOwnOtoInquiries(@PathVariable final Long memberId,
                                                                                                    final Pageable pageable) {
        List<CustomerServicePostRetrieveResponse> inquiryResponses = customerServicePostService.retrieveOwnOtoInquiries(pageable, memberId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_CUSTOMER_SERVICE + "/oto-inquiries/members/" + memberId))
                             .body(inquiryResponses);
    }

    @DeleteMapping("/oto-inquiries/{inquiryId}/members/{memberId}")
    public ResponseEntity<Void> deleteOwnOtoInquiry(@PathVariable final Long inquiryId,
                                                    @PathVariable final Long memberId) {
        customerServicePostService.deleteOwnOtoInquiry(inquiryId, memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create(DEFAULT_CUSTOMER_SERVICE + "/oto-inquiries/" + inquiryId + "/members/" + memberId))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

}
