package com.nhnacademy.marketgg.server.repository.deliveryaddress;

import com.nhnacademy.marketgg.server.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원의 배송지 Repository 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long>,
        DeliveryAddressRepositoryCustom {

}
