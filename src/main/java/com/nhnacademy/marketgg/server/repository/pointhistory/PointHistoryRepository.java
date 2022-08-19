package com.nhnacademy.marketgg.server.repository.pointhistory;

import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 포인트 내역 관리 Repository 입니다.
 *
 * @author 박세완, 김정민
 * @version 1.0.0
 */
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long>, PointHistoryRepositoryCustom {

    /**
     * 회원을 받아 해당 내역을 찾습니다.
     *
     * @param referrerMember - 추천을 받은 회원입니다.
     * @return 포인트 내역을 반환합니다.
     */
    Optional<PointHistory> findByMember(Member referrerMember);
}
