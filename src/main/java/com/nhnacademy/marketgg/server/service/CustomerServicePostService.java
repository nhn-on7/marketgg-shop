package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * 고객센터 서비스입니다.
 *
 * @version 1.0.0
 */
public interface CustomerServicePostService {

    /**
     * 고객센터 게시글 단건을 조회하는 메소드입니다.
     *
     * @param csPostId - 고객센터 게시글의 식별번호입니다.
     * @return 조회한 게시글의 정보를 담은 DTO 객체를 반환합니다.
     * @since 1.0.0
     */
    PostResponseForOtoInquiry retrieveCustomerServicePost(final Long csPostId);

    /**
     * 1:1 문의 전체 목록을 조회하는 메소드입니다.
     *
     * @param pageable - 페이징 처리를 위한 객체입니다.
     * @return 1:1 문의 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<PostResponseForOtoInquiry> retrieveOtoInquiries(final Pageable pageable);

    /**
     * 회원 본인의 1:1 전체 목록을 조회하는 메소드입니다.
     *
     * @param pageable - 페이징 처리를 위한 객체입니다.
     * @param memberId - 조회하는 회원의 식별번호입니다.
     * @return 회원의 1:1 문의 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<PostResponseForOtoInquiry> retrieveOwnOtoInquiries(final Pageable pageable, final Long memberId);

    /**
     * 1:1 문의를 등록하는 메소드입니다.
     *
     * @param memberId               - 등록하는 회원의 식별번호입니다.
     * @param postRequest - 1:1 문의를 등록하기 위한 DTO 객체입니다.
     */
    void createOtoInquiry(final Long memberId, final PostRequest postRequest);

    /**
     * 고객센터 게시글을 삭제하는 메소드입니다.
     *
     * @param csPostId - 삭제할 고객센터 게시글의 식별번호입니다.
     * @since 1.0.0
     */
    void deleteCustomerServicePost(Long csPostId);

    void updateInquiryStatus(Long inquiryId, String status);

}
