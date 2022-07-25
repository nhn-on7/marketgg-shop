package com.nhnacademy.marketgg.server.repository.member;

import com.nhnacademy.marketgg.server.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 회원 레포지토리 입니다.
 *
 * @version 1.0.0
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    /**
     * UUID 로 사용자를 조회합니다.
     *
     * @param uuid - 조회하려는 사용자의 UUID
     * @return - 사용자 엔티티 반환
     */
    Optional<Member> findByUuid(String uuid);

}
