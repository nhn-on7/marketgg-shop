package com.nhnacademy.marketgg.server.repository.deliveryaddress;

import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface DeliveryAddressRepositoryCustom {

    List<DeliveryAddressResponse> findDeliveryAddressesByMemberId(final Long memberId);

}
