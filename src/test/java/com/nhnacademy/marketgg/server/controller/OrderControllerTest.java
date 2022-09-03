package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.aop.AuthInfoAspect;
import com.nhnacademy.marketgg.server.aop.MemberInfoAspect;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.AuthInfo;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.order.CartOrderRequest;
import com.nhnacademy.marketgg.server.dto.request.order.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.order.OrderUpdateStatusRequest;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.nhnacademy.marketgg.server.aop.AspectUtils.AUTH_ID;
import static com.nhnacademy.marketgg.server.aop.AspectUtils.WWW_AUTHENTICATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
@ActiveProfiles({ "testdb", "common", "local" })
@Import({
        AuthInfoAspect.class,
        MemberInfoAspect.class
})
class OrderControllerTest {

    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    OrderService orderService;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    RestTemplate restTemplate;

    String baseUri = "/orders";

    MemberInfo memberInfo;
    AuthInfo authInfo;
    HttpHeaders headers;
    String uuid;
    ShopResult<AuthInfo> authInfoResponse;

    @BeforeEach
    void setUp(WebApplicationContext wac) throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                                 .alwaysDo(print())
                                 .build();

        uuid = UUID.randomUUID().toString();
        memberInfo = Dummy.getDummyMemberInfo(1L, new Cart());
        authInfo = Dummy.getDummyAuthInfo();
        given(memberRepository.findMemberInfoByUuid(uuid)).willReturn(Optional.of(memberInfo));

        String roles = mapper.writeValueAsString(Collections.singletonList(Role.ROLE_USER));
        headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "jwt");
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATE, roles);
        authInfoResponse = ShopResult.successWith(authInfo);
    }

    @Test
    @DisplayName("주문 등록")
    void testCreateOrder() throws Exception {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        OrderToPayment orderToPayment = new OrderToPayment("GGORDER_1", "orderName", "name",
                                                           "email", 30000L, 1L,
                                                           2000, 300);

        given(orderService.createOrder(any(OrderCreateRequest.class), any(MemberInfo.class))).willReturn(
                orderToPayment);

        mockMvc.perform(post(baseUri)
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderCreateRequest)))
               .andExpect(status().isCreated());

        then(orderService).should(times(1)).createOrder(any(OrderCreateRequest.class), any(MemberInfo.class));
    }

    @Test
    @DisplayName("주문서 폼 필요정보 조회")
    void testRetrieveOrderForm() throws Exception {
        OrderFormResponse orderFormResponse = Dummy.getDummyOrderFormResponse();
        CartOrderRequest cartOrderRequest = new CartOrderRequest();
        ReflectionTestUtils.setField(cartOrderRequest, "id", List.of(1L));
        ResponseEntity<ShopResult<AuthInfo>> responseEntity = new ResponseEntity<>(authInfoResponse, HttpStatus.OK);

        given(orderService.retrieveOrderForm(any(List.class), any(MemberInfo.class), any(AuthInfo.class)))
                .willReturn(orderFormResponse);
        given(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(
                ParameterizedTypeReference.class)))
                .willReturn(responseEntity);

        mockMvc.perform(post(baseUri + "/order-form")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(cartOrderRequest)))
               .andExpect(status().isOk());

        then(orderService).should(times(1))
                          .retrieveOrderForm(any(List.class), any(MemberInfo.class), any(AuthInfo.class));
    }

    @Test
    @DisplayName("주문서 목록 조회")
    void testRetrieveOrderList() throws Exception {
        given(orderService.retrieveOrderList(any(MemberInfo.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(Dummy.getOrderRetrieveResponse())));

        mockMvc.perform(get(baseUri)
                                .param("page", "1")
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

        then(orderService).should(times(1))
                          .retrieveOrderList(any(MemberInfo.class), any(Pageable.class));
    }

    @Test
    @DisplayName("주문 상세 조회")
    void testRetrieveOrderDetail() throws Exception {
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
    void testUpdateOrderStatus() throws Exception {
        OrderUpdateStatusRequest updateStatus = new OrderUpdateStatusRequest();

        willDoNothing().given(orderService).updateStatus(anyLong(), any(OrderUpdateStatusRequest.class));

        mockMvc.perform(patch(baseUri + "/{orderId}/status", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(updateStatus)))
               .andExpect(status().isOk());

        then(orderService).should(times(1))
                          .updateStatus(anyLong(), any(OrderUpdateStatusRequest.class));
    }

    @Test
    @DisplayName("주문 운송장 번호 발급 요청")
    void testCreateTrackingNo() throws Exception {
        willDoNothing().given(orderService).createTrackingNo(anyLong());

        mockMvc.perform(patch(baseUri + "/{orderId}/delivery", 1L)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

        then(orderService).should(times(1)).createTrackingNo(anyLong());
    }

    @Test
    @DisplayName("주문 취소")
    void testCancelOrder() throws Exception {
        willDoNothing().given(orderService).cancelOrder(anyLong());

        mockMvc.perform(patch(baseUri + "/{orderId}", 1L)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

        then(orderService).should(times(1)).cancelOrder(anyLong());
    }

}
