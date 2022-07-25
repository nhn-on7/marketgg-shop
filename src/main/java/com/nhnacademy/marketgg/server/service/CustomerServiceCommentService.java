package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.response.CustomerServiceCommentDto;

public interface CustomerServiceCommentService {
    void createComment(Long inquiryId, CustomerServiceCommentDto customerServiceCommentDto);

}
