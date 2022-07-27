package com.nhnacademy.marketgg.server.repository.customerservicecomment;

import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 고객센터 게시글의 댓글 레포지토리입니다.
 *
 * @version 1.0.0
 */
public interface CustomerServiceCommentRepository extends JpaRepository<CustomerServiceComment, Long>, CustomerServiceCommentRepositoryCustom {

}
