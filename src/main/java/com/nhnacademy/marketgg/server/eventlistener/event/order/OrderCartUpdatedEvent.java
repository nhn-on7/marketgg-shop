package com.nhnacademy.marketgg.server.eventlistener.event.order;

import com.nhnacademy.marketgg.server.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class OrderCartUpdatedEvent {

    private Order order;

    private List<Long> productIds;

}
