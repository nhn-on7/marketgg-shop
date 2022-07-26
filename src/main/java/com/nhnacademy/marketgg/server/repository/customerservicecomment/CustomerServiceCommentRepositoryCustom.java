package com.nhnacademy.marketgg.server.repository.customerservicecomment;

import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CustomerServiceCommentRepositoryCustom {

    /**
     * 고객센터 게시글의 번호에 따라 댓글 목록을 조회하는 메소드입니다.
     *
     * @param inquiryId - 게시글의 식별번호입니다.
     * @return 조회한 댓글 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<CustomerServiceComment> findByInquiry(Long inquiryId);

}
