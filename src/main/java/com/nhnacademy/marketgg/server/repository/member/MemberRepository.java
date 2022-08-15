package com.nhnacademy.marketgg.server.repository.member;

import com.nhnacademy.marketgg.server.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원 레포지토리 입니다.
 *
 * @author 박세완, 김정민, 민아영, 윤동열
 * @version 1.0.0
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    /**
     * 회원의 uuid 를 통해 회원 정보를 조회하는 메소드입니다.
     *
     * @param uuid - 조회할 회원의 uuid 입니다.
     * @return 회원의 정보를 반환합니다.
     * @since 1.0.0
     */
    Optional<Member> findByUuid(String uuid);

}
