package com.nhnacademy.marketgg.server.repository.orderdeliveryaddress;

import com.nhnacademy.marketgg.server.entity.OrderDeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문 배송지 Repository 입니다.
 *
 * @version 1.0.0
 * @author 김정민
 */
public interface OrderDeliveryAddressRepository extends JpaRepository<OrderDeliveryAddress, OrderDeliveryAddress.Pk>,
    OrderDeliveryAddressRepositoryCustom {

}
