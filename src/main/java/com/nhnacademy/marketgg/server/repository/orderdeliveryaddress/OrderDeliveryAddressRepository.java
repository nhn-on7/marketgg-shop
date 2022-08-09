package com.nhnacademy.marketgg.server.repository.orderdeliveryaddress;

import com.nhnacademy.marketgg.server.entity.OrderDeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDeliveryAddressRepository extends JpaRepository<OrderDeliveryAddress, OrderDeliveryAddress.Pk>,
    OrderDeliveryAddressRepositoryCustom {

}
