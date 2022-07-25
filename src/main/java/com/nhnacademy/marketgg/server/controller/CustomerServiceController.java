package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostRetrieveResponse;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/oto-inquiries/members/{memberId}")
    public ResponseEntity<List<CustomerServicePostRetrieveResponse>> retrieveOwnCustomerServicePost(@PathVariable final Long memberId,
                                                                                                    final Pageable pageable) {
        List<CustomerServicePostRetrieveResponse> inquiryResponses = customerServicePostService.retrieveOwnOtoInquiries(pageable, memberId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_CUSTOMER_SERVICE + "oto-inquiries/members/" + memberId))
                             .body(inquiryResponses);
    }

}
