package com.nhnacademy.marketgg.server.repository.pointhistory;

import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 포인트 내역 Repository 입니다.
 *
 * @version 1.0.0
 */
import java.util.Optional;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long>, PointHistoryRepositoryCustom {

    Optional<PointHistory> findByMember(Member referrerMember);
}
