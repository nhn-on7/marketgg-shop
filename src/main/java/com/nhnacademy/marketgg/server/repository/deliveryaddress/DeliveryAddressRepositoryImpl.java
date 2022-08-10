package com.nhnacademy.marketgg.server.repository.deliveryaddress;

import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import com.nhnacademy.marketgg.server.entity.QDeliveryAddress;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class DeliveryAddressRepositoryImpl extends QuerydslRepositorySupport implements DeliveryAddressRepositoryCustom {

    public DeliveryAddressRepositoryImpl() {
        super(DeliveryAddress.class);
    }

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

}
