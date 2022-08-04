package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.dto.response.OrderResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface OrderRepositoryCustom {

    List<OrderResponse> findAllOrder();

    List<OrderResponse> findOrderListById(Long memberId);

}
