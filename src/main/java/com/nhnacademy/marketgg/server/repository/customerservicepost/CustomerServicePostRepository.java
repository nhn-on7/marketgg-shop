package com.nhnacademy.marketgg.server.repository.customerservicepost;

import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerServicePostRepository extends JpaRepository<CustomerServicePost, Long>, CustomerServicePostRepositoryCustom {

}
