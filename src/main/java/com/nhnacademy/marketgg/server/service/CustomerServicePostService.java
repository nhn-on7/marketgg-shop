package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostDto;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * 고객센터 서비스입니다.
 *
 * @version 1.0.0
 */
public interface CustomerServicePostService {

    /**
     * 1:1 문의 단건을 조회하는 메소드입니다.
     *
     * @param inquiryId - 1:1 문의의 식별번호입니다.
     * @return 조회한 1:1 문의의 정보를 담은 DTO 객체를 반환합니다.
     * @since 1.0.0
     */
    CustomerServicePostDto retrieveOtoInquiry(Long inquiryId);

    /**
     * 1:1 문의 전체 목록을 조회하는 메소드입니다.
     *
     * @param pageable - 페이징 처리를 위한 객체입니다.
     * @return 1:1 문의 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<CustomerServicePostDto> retrieveOtoInquiries(final Pageable pageable);

    /**
     * 1:1 문의를 삭제하는 메소드입니다.
     *
     * @param inquiryId - 삭제할 1:1 문의의 식별번호입니다.
     * @since 1.0.0
     */
    void deleteOtoInquiry(Long inquiryId);

    /**
     * 회원 본인의 1:1 전체 목록을 조회하는 메소드입니다.
     *
     * @param pageable - 페이징 처리를 위한 객체입니다.
     * @param memberId - 조회하는 회원의 식별번호입니다.
     * @return 회원의 1:1 문의 전체 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<CustomerServicePostDto> retrieveOwnOtoInquiries(Pageable pageable, Long memberId);

    /**
     * 회원 본인의 1:1 문의 단건을 조회하는 메소드입니다.
     *
     * @param inquiryId - 조회할 1:1 문의의 식별번호입니다.
     * @param memberId - 조회하는 회원의 식별번호입니다.
     * @return 1:1 문의 단건의 정보가 담긴 DTO 객체를 반환합니다.
     * @since 1.0.0
     */
    CustomerServicePostDto retrieveOwnOtoInquiry(Long inquiryId, Long memberId);

    /**
     * 회원 본인의 1:1 문의 단건을 삭제하는 메소드입니다.
     *
     * @param inquiryId - 삭제할 1:1 문의의 식별번호입니다.
     * @param memberId - 삭제하는 회원의 식별번호입니다.
     * @since 1.0.0
     */
    void deleteOwnOtoInquiry(Long inquiryId, Long memberId);

    /**
     * 1:1 문의를 등록하는 메소드입니다.
     *
     * @param memberId - 등록하는 회원의 식별번호입니다.
     * @param customerServicePostDto - 1:1 문의를 등록하기 위한 DTO 객체입니다.
     */
    void createOtoInquiry(Long memberId, CustomerServicePostDto customerServicePostDto);

}
