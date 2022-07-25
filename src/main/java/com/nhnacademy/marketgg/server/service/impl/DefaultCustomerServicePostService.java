package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import com.nhnacademy.marketgg.server.exception.category.CategoryNotFoundException;
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
    public CustomerServicePostRetrieveResponse retrieveOtoInquiry(Long inquiryId) {
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY).orElseThrow(
                CategoryNotFoundException::new);
        CustomerServicePost otoInquiry = customerServicePostRepository.findOtoInquiry(categoryId, inquiryId);

        return customerServicePostMapper.toDto(otoInquiry);
    }

    @Override
    public List<CustomerServicePostRetrieveResponse> retrieveOtoInquiries(final Pageable pageable) {
        String categoryId = categoryRepository.retrieveCategoryIdByName(OTO_INQUIRY).orElseThrow(
                CategoryNotFoundException::new);
        List<CustomerServicePost> otoInquiries = customerServicePostRepository.findAllOtoInquires(pageable, categoryId)
                                                                              .getContent();

        return otoInquiries.stream()
                           .map(customerServicePostMapper::toDto)
                           .collect(Collectors.toUnmodifiableList());
    }

}
