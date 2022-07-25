package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostRetrieveResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerServicePostService {

    CustomerServicePostRetrieveResponse retrieveOtoInquiry(Long inquiryId);

    List<CustomerServicePostRetrieveResponse> retrieveOtoInquiries(final Pageable pageable);

    void deleteOtoInquiry(Long inquiryId);

    List<CustomerServicePostRetrieveResponse> retrieveOwnOtoInquiries(Pageable pageable, Long memberId);

}
