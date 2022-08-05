package com.nhnacademy.marketgg.server.repository.order;

import com.nhnacademy.marketgg.server.dto.response.OrderDetailResponse;
import com.nhnacademy.marketgg.server.dto.response.OrderResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface OrderRepositoryCustom {

    List<OrderResponse> findOrderList(Long id, boolean user);

    OrderDetailResponse findOrderDetail(Long orderId, Long memberId, boolean isUser);

}
