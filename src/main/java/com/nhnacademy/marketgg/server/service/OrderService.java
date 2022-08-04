package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    void createOrder(OrderCreateRequest orderRequest, Long memberId);

    List<OrderResponse> retrieveOrderList(Long memberId, boolean isUser);

}
