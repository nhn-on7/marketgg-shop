package com.nhnacademy.marketgg.server.repository.member;

import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * QueryDsl 을 사용하기 위한 Repository 인터페이스입니다.
 *
 * @version 1.0.0
 */
@NoRepositoryBean
public interface MemberRepositoryCustom {

    /**
     * 회원의 uuid 로 회원정보를 조회하기 위한 메소드입니다.
     *
     * @param uuid - 조회할 회원의 uuid 입니다.
     * @return 회원의 정보를 반환합니다.
     * @since 1.0.0
     */
    Optional<MemberInfo> findMemberInfoByUuid(final String uuid);

    /**
     * 해당 주문의 회원 uuid 를 조회하기 위한 메소드입니다.
     * @param orderId - 주문의 식별번호입니다.
     * @return 주문을 한 회원의 uuid 를 반환합니다.
     * @since 1.0.0
     */
    String findUuidByOrderId(final Long orderId);

}
