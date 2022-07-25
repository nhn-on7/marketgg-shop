package com.nhnacademy.marketgg.server.repository.customerservicecomment;

import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerServiceCommentRepository extends JpaRepository<CustomerServiceComment, Long>, CustomerServiceCommentRepositoryCustom {

}
