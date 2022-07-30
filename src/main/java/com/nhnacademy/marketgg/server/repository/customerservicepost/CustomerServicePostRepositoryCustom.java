package com.nhnacademy.marketgg.server.repository.customerservicepost;

import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.server.entity.CustomerServicePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomerServicePostRepositoryCustom {


    PostResponseForOtoInquiry findOtoInquiryById(final Long inquiryId);
    /**
     * 카테고리 번호에 따라 고객센터 게시글(공지사항, 1:1문의, FAQ) 목록을 조회하는 메소드입니다.
     *
     * @param pageable   - 페이징 처리를 위한 객체입니다.
     * @param categoryId - 조회할 게시글 목록의 카테고리 식별번호입니다.
     * @return 카테고리 번호에 따른 게시글 목록을 Page 로 반환합니다.
     * @since 1.0.0
     */
    Page<PostResponseForOtoInquiry> findPostsByCategoryId(final Pageable pageable, final String categoryId);

    /**
     * 카테고리 번호와 회원 번호에 따라 고객센터 게시글 목록을 조회하는 메소드입니다.
     *
     * @param pageable   - 페이징 처리를 위한 객체입니다.
     * @param categoryId - 조회할 게시글 목록의 카테고리 식별번호입니다.
     * @param memberId   - 조회할 게시글 목록의 작성자인 회원의 식별번호입니다.
     * @return 카테고리 번호와 회원 번호에 따른 게시글 목록을 Page 로 반환합니다.
     * @since 1.0.0
     */
    Page<PostResponseForOtoInquiry> findPostByCategoryAndMember(final Pageable pageable, final String categoryId, final Long memberId);

}
