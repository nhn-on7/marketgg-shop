package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.service.order.DefaultOrderService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
public class DefaultOrderServiceTest {

    @InjectMocks
    DefaultOrderService orderService;


}
