package com.nhnacademy.marketgg.server.repository.cart.customerservicecomment;

import com.nhnacademy.marketgg.server.entity.CustomerServiceComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 고객센터 게시글의 댓글 Repository 입니다.
 *
 * @author 박세완, 김정민
 * @version 1.0.0
 */
public interface CustomerServiceCommentRepository extends JpaRepository<CustomerServiceComment, Long>, CustomerServiceCommentRepositoryCustom {

    /**
     * 삭제할 게시글 번호로 연관 댓글목록을 전체 삭제하는 메소드입니다.
     *
     * @param postNo - 삭제할 게시글의 식별번호입니다.
     * @since 1.0.0
     */
    void deleteAllByCustomerServicePost_Id(final Long postNo);

}
