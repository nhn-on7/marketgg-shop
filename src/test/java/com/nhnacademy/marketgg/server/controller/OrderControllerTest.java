package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.aop.AuthInjectAspect;
import com.nhnacademy.marketgg.server.aop.MemberInfoAspect;
import com.nhnacademy.marketgg.server.aop.RoleCheckAspect;
import com.nhnacademy.marketgg.server.aop.UuidAspect;
import com.nhnacademy.marketgg.server.dto.info.AuthInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.order.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.order.OrderUpdateStatusRequest;
import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import com.nhnacademy.marketgg.server.dto.response.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderFormResponse;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.nhnacademy.marketgg.server.aop.AspectUtils.AUTH_ID;
import static com.nhnacademy.marketgg.server.aop.AspectUtils.WWW_AUTHENTICATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@ActiveProfiles({ "testdb", "common", "local"})
@Import({
        RoleCheckAspect.class,
        AuthInjectAspect.class,
        UuidAspect.class,
        MemberInfoAspect.class
})
public class OrderControllerTest {

    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    OrderService orderService;

    @MockBean
    MemberRepository memberRepository;

    String baseUri = "/orders";

    MemberInfo memberInfo;
    AuthInfo authInfo;
    HttpHeaders headers;
    String uuid;
    Long memberId = 1L;

    @BeforeEach
    void setUp(WebApplicationContext wac) throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                                 .alwaysDo(print())
                                 .build();

        uuid = UUID.randomUUID().toString();
        memberInfo = Dummy.getDummyMemberInfo(memberId, new Cart());
        authInfo = Dummy.getDummyAuthInfo();
        given(memberRepository.findMemberInfoByUuid(uuid)).willReturn(Optional.of(memberInfo));

        String roles = mapper.writeValueAsString(Collections.singletonList(Role.ROLE_USER));
        headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "jwt");
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATE, roles);
    }

    @Test
    @DisplayName("주문 등록")
    public void testCreateOrder() throws Exception {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        OrderToPayment orderToPayment = new OrderToPayment("GGORDER_1", "orderName", "name",
                                                           "email", 30000L, 1L,
                                                           2000, 300);

        given(orderService.createOrder(any(OrderCreateRequest.class), anyLong())).willReturn(orderToPayment);

        mockMvc.perform(post(baseUri)
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderCreateRequest)))
               .andExpect(status().isCreated());

        then(orderService).should(times(1)).createOrder(any(OrderCreateRequest.class), anyLong());
    }

    // memo: I/O error on GET request for "http://127.0.0.1:6060/auth/v1/info" connection error -> gateway, auth 켜야함
    // memo: 500 Internal Server Error: "{"success":false,"message":null}" -> authInfo 받는 과정 오류 jwt 때문?
    // @Test
    // @DisplayName("주문서 폼 필요정보 조회")
    // public void testRetrieveOrderForm() throws Exception {
    //     OrderFormResponse orderFormResponse = Dummy.getDummyOrderFormResponse();
    //     List<ProductToOrder> list = List.of(new ProductToOrder());
    //
    //     given(orderService.retrieveOrderForm(any(List.class), any(MemberInfo.class), any(AuthInfo.class)))
    //             .willReturn(orderFormResponse);
    //
    //     mockMvc.perform(get(baseUri + "/order-form")
    //                             .headers(headers)
    //                             .contentType(MediaType.APPLICATION_JSON)
    //                             .content(mapper.writeValueAsString(list)))
    //            .andExpect(status().isOk());
    //
    //     then(orderService).should(times(1))
    //                       .retrieveOrderForm(any(List.class), any(MemberInfo.class), any(AuthInfo.class));
    // }

    @Test
    @DisplayName("주문서 목록 조회")
    public void testRetrieveOrderList() throws Exception {
        given(orderService.retrieveOrderList(any(MemberInfo.class))).willReturn(List.of());

        mockMvc.perform(get(baseUri)
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

        then(orderService).should(times(1)).retrieveOrderList(any(MemberInfo.class));
    }

    @Test
    @DisplayName("주문 상세 조회")
    public void testRetrieveOrderDetail() throws Exception {
        OrderDetailRetrieveResponse orderDetail = Dummy.getDummyOrderDetailResponse();

        given(orderService.retrieveOrderDetail(anyLong(), any(MemberInfo.class))).willReturn(orderDetail);

        mockMvc.perform(get(baseUri + "/{orderId}", 1L)
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

        then(orderService).should(times(1))
                          .retrieveOrderDetail(anyLong(), any(MemberInfo.class));
    }

    @Test
    @DisplayName("주문 상태 변경")
    public void testUpdateOrderStatus() throws Exception {
        OrderUpdateStatusRequest updateStatus = new OrderUpdateStatusRequest();

        willDoNothing().given(orderService).updateStatus(anyLong(), any(OrderUpdateStatusRequest.class));

        mockMvc.perform(patch(baseUri + "/{orderId}/status", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(updateStatus)))
               .andExpect(status().isOk());

        then(orderService).should(times(1))
                          .updateStatus(anyLong(), any(OrderUpdateStatusRequest.class));
    }

}
