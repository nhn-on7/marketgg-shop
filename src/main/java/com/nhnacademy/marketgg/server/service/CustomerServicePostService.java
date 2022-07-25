package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerServicePostService {

    CustomerServicePostDto retrieveOtoInquiry(Long inquiryId);

    List<CustomerServicePostDto> retrieveOtoInquiries(final Pageable pageable);

    void deleteOtoInquiry(Long inquiryId);

    List<CustomerServicePostDto> retrieveOwnOtoInquiries(Pageable pageable, Long memberId);

    CustomerServicePostDto retrieveOwnOtoInquiry(Long inquiryId, Long memberId);

    void deleteOwnOtoInquiry(Long inquiryId, Long memberId);

    void createOtoInquiry(Long memberId, CustomerServicePostDto customerServicePostDto);

}
