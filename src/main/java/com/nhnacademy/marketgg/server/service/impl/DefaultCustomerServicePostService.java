package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CustomerServicePostDto;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.exception.category.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.exception.customerservicepost.CustomerServicePostNotFoundException;
import com.nhnacademy.marketgg.server.mapper.impl.CustomerServicePostMapper;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.server.repository.customerservicepost.CustomerServicePostRepository;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultCustomerServicePostService implements CustomerServicePostService {

    private final CustomerServicePostMapper customerServicePostMapper;
    private final CustomerServicePostRepository customerServicePostRepository;
    private final CategoryRepository categoryRepository;

    private static String OTO_INQUIRY = "1:1문의";

    @Override
    public CustomerServicePostDto retrieveOtoInquiry(Long inquiryId) {
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY).orElseThrow(
                CategoryNotFoundException::new);
        return null;
        // memo categoryId + inquiryId 로 1:1 문의 단건 조회하고 있던 중
    }

    @Override
    public List<CustomerServicePostDto> retrieveOtoInquiries(final Pageable pageable) {
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY).orElseThrow(
                CategoryNotFoundException::new);
        List<CustomerServicePost> otoInquiries = customerServicePostRepository.findAllOtoInquires(pageable, categoryId)
                                                                              .getContent();

        return otoInquiries.stream()
                           .map(customerServicePostMapper::toDto)
                           .collect(Collectors.toUnmodifiableList());
    }

}
