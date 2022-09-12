package com.nhnacademy.marketgg.server.eventlistener.event.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class OrderProductUpdatedEvent {

    private List<Long> productIds;

    private List<Integer> productAmounts;

}
