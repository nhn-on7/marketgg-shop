package com.nhnacademy.marketgg.server.repository.orderproduct;

import com.nhnacademy.marketgg.server.dto.response.orderproduct.OrderProductResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface OrderProductRepositoryCustom {
    List<OrderProductResponse> findByOrderId(Long orderId);

}
