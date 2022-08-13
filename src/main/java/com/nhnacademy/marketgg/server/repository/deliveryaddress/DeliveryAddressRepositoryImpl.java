package com.nhnacademy.marketgg.server.repository.deliveryaddress;

import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.QDeliveryAddress;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * QueryDsl 사용을 위한 CustomRepository 구현체 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
public class DeliveryAddressRepositoryImpl extends QuerydslRepositorySupport
    implements DeliveryAddressRepositoryCustom {

    public DeliveryAddressRepositoryImpl() {
        super(DeliveryAddress.class);
    }

    /**
     * 회원의 식별번호로 회원이 가지고 있는 모든 배송지를 반환합니다.
     *
     * @param id - 전체 배송지를 조회하는 회원의 식별번호 입니다.
     * @return 회원이 가지고 있는 모든 배송지를 담은 List 입니다.
     */
    @Override
    public List<DeliveryAddressResponse> findDeliveryAddressesByMemberId(Long id) {
        QDeliveryAddress deliveryAddress = QDeliveryAddress.deliveryAddress;

        return from(deliveryAddress)
            .where(deliveryAddress.member.id.eq(id))
            .select(Projections.constructor(DeliveryAddressResponse.class,
                deliveryAddress.id,
                deliveryAddress.isDefaultAddress,
                deliveryAddress.zipCode,
                deliveryAddress.address,
                deliveryAddress.detailAddress))
            .fetch();
    }

}
