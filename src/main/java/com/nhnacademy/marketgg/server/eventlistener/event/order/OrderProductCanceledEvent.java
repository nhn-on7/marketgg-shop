package com.nhnacademy.marketgg.server.eventlistener.event.order;

import com.nhnacademy.marketgg.server.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderProductCanceledEvent {

    private Order order;

}
