package com.nhnacademy.marketgg.server.repository.orderdeliveryaddress;

import com.nhnacademy.marketgg.server.dto.response.orderdeliveryaddress.OrderDeliveryAddressResponse;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface OrderDeliveryAddressRepositoryCustom {

    /**
     * 주문 번호로 주문 배송지를 조회하는 메소드입니다.
     *
     * @param orderId - 주문의 식별번호입니다.
     * @return 조회한 주문 배송지를 반환합니다.
     * @since 1.0.0
     */
    OrderDeliveryAddressResponse findByOrderId(Long orderId);

}
