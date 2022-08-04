package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;

}
