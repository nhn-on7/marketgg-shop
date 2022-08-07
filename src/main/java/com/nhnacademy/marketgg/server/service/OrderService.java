package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.OrderCreateResponse;
import com.nhnacademy.marketgg.server.dto.response.OrderDetailResponse;
import com.nhnacademy.marketgg.server.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderCreateResponse createOrder(final OrderCreateRequest orderRequest, final Long memberId);

    List<OrderResponse> retrieveOrderList(final MemberInfo memberInfo);

    OrderDetailResponse retrieveOrderDetail(final Long orderId, final MemberInfo memberInfo);

    void deleteOrder(Long orderId);

}
