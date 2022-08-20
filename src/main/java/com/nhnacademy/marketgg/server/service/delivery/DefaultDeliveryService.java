package com.nhnacademy.marketgg.server.service.delivery;

import com.nhnacademy.marketgg.server.dto.request.delivery.CreatedTrackingNoRequest;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.exception.order.OrderNotFoundException;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@inheritDoc}
 *
 * @author 김훈민
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DefaultDeliveryService implements DeliveryService {

    private final OrderRepository orderRepository;

    /**
     * {@inheritDoc}
     *
     * @param createdTrackingNoRequest - 운송장 번호와 주문 정보 입니다
     */
    @Transactional
    @Override
    public void createdTrackingNo(final CreatedTrackingNoRequest createdTrackingNoRequest) {
        Order order = orderRepository.findById(Long.parseLong(createdTrackingNoRequest.getOrderNo()))
                                     .orElseThrow(OrderNotFoundException::new);

        order.insertTrackingNo(createdTrackingNoRequest.getTrackingNo());
    }

}
