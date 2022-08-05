package com.nhnacademy.marketgg.server.repository.member;

import com.nhnacademy.marketgg.server.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원 레포지토리 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByUuid(String uuid);
}
