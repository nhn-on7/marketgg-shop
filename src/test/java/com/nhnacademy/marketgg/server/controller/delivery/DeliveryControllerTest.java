package com.nhnacademy.marketgg.server.controller.delivery;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.aop.MemberInfoAspect;
import com.nhnacademy.marketgg.server.aop.UuidAspect;
import com.nhnacademy.marketgg.server.dto.request.delivery.CreatedTrackingNoRequest;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.service.delivery.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@Transactional
@SpringBootTest
@ActiveProfiles({ "testdb", "common", "local" })
@Import({
    UuidAspect.class,
    MemberInfoAspect.class
})
class DeliveryControllerTest {
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    DeliveryService deliveryService;

    @MockBean
    OrderRepository orderRepository;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                                 .alwaysDo(print())
                                 .build();
    }

    @Test
    @DisplayName("배송 번호 추가")
    void testCreatedTrackingNo() throws Exception {
        CreatedTrackingNoRequest testTrackingNoRequest = CreatedTrackingNoRequest.builder()
                                                                                 .trackingNo(
                                                                                     "서른여섯자리-서른여섯자리-서른여섯자리-서른여섯자리-서른여섯자리^^")
                                                                                 .orderNo("1234")
                                                                                 .build();

        willDoNothing().given(deliveryService).createdTrackingNo(testTrackingNoRequest);

        mockMvc.perform(post("/delivery")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(testTrackingNoRequest)))
               .andExpect(status().isOk());

        then(deliveryService).should(times(1))
                             .createdTrackingNo(any(CreatedTrackingNoRequest.class));
    }

}
