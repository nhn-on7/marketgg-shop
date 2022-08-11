package com.nhnacademy.marketgg.server.repository.orderdeliveryaddress;

import com.nhnacademy.marketgg.server.dto.response.orderdeliveryaddress.OrderDeliveryAddressResponse;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface OrderDeliveryAddressRepositoryCustom {
    OrderDeliveryAddressResponse findByOrderId(Long orderId);

}
