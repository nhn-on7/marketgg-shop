package com.nhnacademy.marketgg.server.service.product;

import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.review.ReviewCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.review.ReviewUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.review.ReviewResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * 리뷰 서비스입니다.
 *
 * @author 조현진
 * @version 1.0.0
 */
public interface ReviewService {

    /**
     * 사진이 첨부된 리뷰를 생성합니다.
     *
     * @param reviewRequest - 리뷰 생성을 위한 dto입니다.
     * @param memberInfo - 리뷰를 작성한 회원의 정보입니다.
     */
    void createReview(final ReviewCreateRequest reviewRequest, final MultipartFile image, final MemberInfo memberInfo)
        throws IOException;

    /**
     * 글로만 이루어진 리뷰를 생성합니다.
     *
     * @param reviewRequest - 리뷰 생성을 위한 dto입니다.
     * @param memberInfo - 리뷰를 작성한 회원의 정보입니다.
     */
    void createReview(final ReviewCreateRequest reviewRequest, final MemberInfo memberInfo);

    /**
     * 모든 리뷰를 조회합니다.
     *
     * @param pageable - 사이즈는 10입니다.
     * @return - 페이지 정보가 담긴 공통 응답객체를 반환합니다.
     */
    List<ReviewResponse> retrieveReviews(final Pageable pageable);

    /**
     * 후기의 상세 정보를 조회합니다.
     *
     * @param id - 후기의 기본 키 입니다.
     * @return - ReviewResponse가 담긴 공통 응답객체를 반환합니다.
     */
    ReviewResponse retrieveReviewDetails(final Long id);

    /**
     * 후기를 수정합니다.
     *
     * @param reviewRequest - 후기 수정 정보를 담은 DTO입니다.
     * @param id            - 후기의 식별번호입니다.
     */
    void updateReview(final ReviewUpdateRequest reviewRequest, final Long id);

    /**
     * 후기를 삭제합니다.
     *
     * @param id - 삭제하려는 후기의 기본키입니다.
     */
    void deleteReview(final Long id);

    Boolean makeBestReview(final Long id);

}
