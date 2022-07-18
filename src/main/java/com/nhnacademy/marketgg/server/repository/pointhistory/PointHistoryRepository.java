package com.nhnacademy.marketgg.server.repository.pointhistory;

import com.nhnacademy.marketgg.server.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 포인트 내역 Repository 입니다.
 *
 * @version 1.0.0
 */
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long>, PointHistoryRepositoryCustom {

}
