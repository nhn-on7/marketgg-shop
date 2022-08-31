package com.nhnacademy.marketgg.server.repository.orderproduct;

import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface OrderProductRepositoryCustom {

    /**
     * 주문 번호로 주문 상품 목록을 조회하는 메소드입니다.
     *
     * @param orderId - 주문의 식별번호입니다.
     * @return 조회한 주문 상품 목록을 반환합니다.
     * @since 1.0.0
     */
    List<ProductToOrder> findByOrderId(final Long orderId);

}
