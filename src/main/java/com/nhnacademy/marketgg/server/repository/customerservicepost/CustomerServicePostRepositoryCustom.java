package com.nhnacademy.marketgg.server.repository.customerservicepost;

import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomerServicePostRepositoryCustom {

    CustomerServicePost findOtoInquiry(String categoryId, Long inquiryId);

    Page<CustomerServicePost> findAllOtoInquires(Pageable pageable, String categoryId);
}
