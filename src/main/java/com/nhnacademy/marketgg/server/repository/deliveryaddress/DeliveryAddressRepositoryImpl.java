package com.nhnacademy.marketgg.server.repository.deliveryaddress;

import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.QDeliveryAddress;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

/**
 * QueryDsl 사용을 위한 CustomRepository 구현체 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
public class DeliveryAddressRepositoryImpl extends QuerydslRepositorySupport implements DeliveryAddressRepositoryCustom {

    public DeliveryAddressRepositoryImpl() {
        super(DeliveryAddress.class);
    }

    /**
     * 회원의 식별번호로 회원이 가지고 있는 모든 배송지를 반환합니다.
     *
     * @param memberId - 전체 배송지를 조회하는 회원의 식별번호 입니다.
     * @return 회원이 가지고 있는 모든 배송지를 담은 List 입니다.
     */
    @Override
    public List<DeliveryAddressResponse> findDeliveryAddressesByMemberId(final Long memberId) {
        QDeliveryAddress deliveryAddress = QDeliveryAddress.deliveryAddress;

        return from(deliveryAddress)
                .where(deliveryAddress.member.id.eq(memberId))
                .select(Projections.constructor(DeliveryAddressResponse.class,
                        deliveryAddress.id,
                        deliveryAddress.isDefaultAddress,
                        deliveryAddress.zipCode,
                        deliveryAddress.address,
                        deliveryAddress.detailAddress))
                .fetch();
    }

    @Override
    public DeliveryAddress existsDefaultDeliveryAddress(final Member member) {
        QDeliveryAddress deliveryAddress = QDeliveryAddress.deliveryAddress;

        return from(deliveryAddress)
                .where(deliveryAddress.isDefaultAddress.isTrue(), deliveryAddress.member.id.eq(member.getId()))
                .select(deliveryAddress)
                .fetchOne();
    }

    @Override
    public Integer countMemberAddresses(final Member member) {
        QDeliveryAddress deliveryAddress = QDeliveryAddress.deliveryAddress;

        return Math.toIntExact(from(deliveryAddress)
                .where(deliveryAddress.member.id.eq(member.getId()))
                .select(deliveryAddress)
                .fetchCount());
    }

}
