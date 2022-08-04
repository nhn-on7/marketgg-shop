package com.nhnacademy.marketgg.server.repository.customerservicepost;

import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 고객센터 게시글 레포지토리입니다.
 *
 * @author 박세완, 김정민
 * @version 1.0.0
 */
public interface CustomerServicePostRepository extends JpaRepository<CustomerServicePost, Long>, CustomerServicePostRepositoryCustom {

    Boolean existsByCategory(final Category category);

}
