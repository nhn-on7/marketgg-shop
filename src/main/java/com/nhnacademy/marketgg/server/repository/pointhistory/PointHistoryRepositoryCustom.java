package com.nhnacademy.marketgg.server.repository.pointhistory;

import com.nhnacademy.marketgg.server.dto.response.point.PointRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PointHistoryRepositoryCustom {

    /**
     * 지정한 회원의 전체 포인트 내역을 반환합니다.
     *
     * @param id - 지정한 회원의 식별번호입니다.
     * @param pageable - 페이지 정보입니다.
     * @return 지정한 회원의 전체 포인트 내역을 List 로 반환합니다.
     * @since 1.0.0
     */
    Page<PointRetrieveResponse> findAllByMemberId(final Long id, final Pageable pageable);

    /**
     * 전체 회원의 포인트 내역을 반환합니다.
     *
     * @param pageable - 페이지 정보입니다.
     * @return 전체 회원의 포인트 내역을 List 로 반환합니다.
     * @since 1.0.0
     */
    Page<PointRetrieveResponse> findAllForAdmin(final Pageable pageable);

    /**
     * 지정한 회원의 누적 포인트를 반환합니다.
     *
     * @param memberId - 지정한 회원의 식별번호입니다.
     * @return 지정한 회원의 누적 포인트를 반환합니다.
     * @since 1.0.0
     */
    Integer findLastTotalPoints(final Long memberId);

    /**
     * 특정 주문에 대한 포인트 이력 목록을 반환합니다.
     *
     * @param orderId - 포인트 이력을 조회할 주문의 식별번호입니다.
     * @return 조회한 포인트 이력 목록을 반환합니다.
     * @since 1.0.0
     */
    List<PointHistory> findByOrderId(final Long orderId);

}
