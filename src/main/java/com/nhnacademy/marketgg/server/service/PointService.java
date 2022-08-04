package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.dto.response.point.PointRetrieveResponse;
import java.util.List;

/**
 * 포인트 내역 Service 입니다.
 *
 * @version 1.0.0
 */
public interface PointService {

    /**
     * 지정한 회원의 포인트 내역을 반환합니다.
     *
     * @param id - 지정한 회원의 식별 번호입니다.
     * @return 지정한 회원의 포인트 내역을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<PointRetrieveResponse> retrievePointHistories(final Long id);

    /**
     * 전체 회원의 포인트 내역을 반환합니다.
     *
     * @return 전체 회원의 포인트 내역을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<PointRetrieveResponse> adminRetrievePointHistories();

    /**
     * 주문에 관련하지않은 포인트 내역을 생성합니다.
     *
     * @param id           - 지정한 회원의 식별번호입니다.
     * @param pointRequest - 포인트 내역을 생성하기 위한 DTO 입니다.
     * @since 1.0.0
     */
    void createPointHistory(final Long id, final PointHistoryRequest pointRequest);

    /**
     * 주문에 관련한 포인트 내역을 생성합니다.
     *
     * @param memberId     - 지정한 회원의 식별번호입니다.
     * @param orderId      - 지정한 주문의 식별번호입니다.
     * @param pointRequest - 포인트 내역을 생성하기 위한 DTO 입니다.
     * @since 1.0.0
     */
    void createPointHistoryForOrder(final Long memberId, final Long orderId,
                                    final PointHistoryRequest pointRequest);

}
