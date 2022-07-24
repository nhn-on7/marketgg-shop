package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.CustomerServicePostDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerServicePostService {

    CustomerServicePostDto retrieveOtoInquiry(Long inquiryId);

    List<CustomerServicePostDto> retrieveOtoInquiries(final Pageable pageable);

}
