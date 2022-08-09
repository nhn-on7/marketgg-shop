package com.nhnacademy.marketgg.server.repository.membergrade;

import com.nhnacademy.marketgg.server.entity.MemberGrade;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원 등급 레포지토리 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public interface MemberGradeRepository extends JpaRepository<MemberGrade, Long>, MemberGradeRepositoryCustom {

    Optional<MemberGrade> findByGrade(String member);

}
