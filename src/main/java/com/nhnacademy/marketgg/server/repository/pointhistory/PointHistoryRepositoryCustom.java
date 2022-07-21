package com.nhnacademy.marketgg.server.repository.pointhistory;

import com.nhnacademy.marketgg.server.dto.response.PointRetrieveResponse;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PointHistoryRepositoryCustom {

    /**
     * 지정한 회원의 전체 포인트 내역을 반환합니다.
     *
     * @param id - 지정한 회원의 식별번호입니다.
     * @return 지정한 회원의 전체 포인트 내역을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<PointRetrieveResponse> findAllByMemberId(final Long id);

    /**
     * 전체 회원의 포인트 내역을 반환합니다.
     *
     * @return 전체 회원의 포인트 내역을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<PointRetrieveResponse> findAllForAdmin();

    /**
     * 지정한 회원의 누적 포인트를 반환합니다.
     *
     * @param id - 지정한 회원의 식별번호입니다.
     * @return 지정한 회원의 누적 포인트를 반환합니다.
     * @since 1.0.0
     */
    Integer findLastTotalPoint(final Long id);

}
