package com.nhnacademy.marketgg.server.repository.customerservicecomment;

import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CustomerServiceCommentRepositoryCustom {

    List<CustomerServiceComment> findByInquiry(Long inquiryId);

}
