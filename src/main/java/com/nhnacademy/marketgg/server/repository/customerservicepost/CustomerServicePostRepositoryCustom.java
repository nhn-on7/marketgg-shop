package com.nhnacademy.marketgg.server.repository.customerservicepost;

import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponseForReady;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomerServicePostRepositoryCustom {

    /**
     * 게시글 번호로 1:1 문의를 조회하는 메소드입니다.
     *
     * @param postNo - 조회할 1:1 문의의 번호입니다.
     * @param memberId - 게시글을 조회할 회원의 식별번호입니다.
     * @return 조회한 게시글을 DTO 객체로 반환합니다.
     */
    PostResponseForReady findOwnOtoInquiry(final Long postNo, final Long memberId);

    /**
     * 게시글 번호로 공지사항과 FAQ 를 조회한다.
     *
     * @param postNo - 지정한 게시글의 식별번호입니다.
     * @return 지정한 식별번호로 조회한 게시글의 상세정보를 반환합니다.
     * @since 1.0.0
     */
    PostResponseForReady findByBoardNo(Long postNo);

    /**
     * 카테고리 번호에 따라 고객센터 게시글(공지사항, 1:1문의, FAQ) 목록을 조회하는 메소드입니다.
     *
     * @param pageable   - 페이징 처리를 위한 객체입니다.
     * @param categoryId - 조회할 게시글 목록의 카테고리 식별번호입니다.
     * @return 카테고리 번호에 따른 게시글 목록을 Page 로 반환합니다.
     * @since 1.0.0
     */
    Page<PostResponse> findPostsByCategoryId(final Pageable pageable, final String categoryId);

    /**
     * 카테고리 번호와 회원 번호에 따라 고객센터 게시글 목록을 조회하는 메소드입니다.
     *
     * @param pageable   - 페이징 처리를 위한 객체입니다.
     * @param categoryId - 조회할 게시글 목록의 카테고리 식별번호입니다.
     * @param memberId   - 조회할 게시글 목록의 작성자인 회원의 식별번호입니다.
     * @return 카테고리 번호와 회원 번호에 따른 게시글 목록을 Page 로 반환합니다.
     * @since 1.0.0
     */
    Page<PostResponse> findPostByCategoryAndMember(final Pageable pageable, final String categoryId, final Long memberId);

}
