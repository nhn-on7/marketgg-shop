package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.response.CustomerServiceCommentDto;

import java.util.List;

/**
 * 고객센터(1:1 문의)의 댓글 서비스입니다.
 *
 * @version 1.0.0
 */
public interface CustomerServiceCommentService {

    /**
     * 한 1:1 문의에 대해 댓글을 등록하는 메소드입니다.
     *
     * @param inquiryId - 댓글이 등록될 1:1 문의의 식별번호입니다.
     * @param memberId - 댓글을 등록하는 회원의 식별번호입니다.
     * @param customerServiceCommentDto - 댓글을 등록하기 위한 DTO 객체입니다.
     * @since 1.0.0
     */
    void createComment(Long inquiryId, Long memberId, CustomerServiceCommentDto customerServiceCommentDto);

    /**
     * 댓글 단건을 조회하는 메소드입니다.
     *
     * @param commentId - 조회할 댓글의 식별번호입니다.
     * @return 조회한 댓글의 정보가 담긴 DTO 객체를 반환합니다.
     * @since 1.0.0
     */
    CustomerServiceCommentDto retrieveComment(Long commentId);

    /**
     * 게시글에 등록된 댓글 목록을 조회하는 메소드입니다.
     *
     * @param inquiryId - 댓글이 등록된 게시글의 식별번호입니다.
     * @return 조회한 댓글 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<CustomerServiceCommentDto> retrieveCommentsByInquiry(Long inquiryId);

}
