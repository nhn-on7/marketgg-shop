package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.CustomerServicePostDto;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/shop/v1/admin/customer-services")
@RequiredArgsConstructor
public class AdminCustomerServiceController {

    private final CustomerServicePostService customerServicePostService;

    private static final String DEFAULT_ADMIN_OTOINQUIRY = "/shop/v1/admin/customer-services/oto-inquiries";

    @GetMapping("/oto-inquiries")
    public ResponseEntity<List<CustomerServicePostDto>> retrieveOtoInquiries(final Pageable pageable) {
        List<CustomerServicePostDto> inquiryResponses = customerServicePostService.retrieveOtoInquiries(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_OTOINQUIRY))
                             .body(inquiryResponses);
    }

}
