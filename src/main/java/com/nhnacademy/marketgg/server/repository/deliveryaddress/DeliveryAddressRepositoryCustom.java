package com.nhnacademy.marketgg.server.repository.deliveryaddress;

import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * QueryDsl 의 사용을 위한 배송지 RepositoryCustom 인터페이스 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
@NoRepositoryBean
public interface DeliveryAddressRepositoryCustom {

    /**
     * 회원의 식별번호로 회원이 가지고 있는 모든 배송지를 반환합니다.
     *
     * @param memberId - 전체 배송지를 조회하는 회원의 식별번호 입니다.
     * @return 회원이 가지고 있는 모든 배송지를 담은 List 입니다.
     */
    List<DeliveryAddressResponse> findDeliveryAddressesByMemberId(final Long memberId);

    DeliveryAddress existsDefaultDeliveryAddress(final Member member);

    Integer countMemberAddresses(Member member);

}
