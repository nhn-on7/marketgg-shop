package com.nhnacademy.marketgg.server.controller;

import static com.nhnacademy.marketgg.server.aop.AspectUtils.AUTH_ID;
import static com.nhnacademy.marketgg.server.aop.AspectUtils.WWW_AUTHENTICATION;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.aop.AuthInjectAspect;
import com.nhnacademy.marketgg.server.aop.RoleCheckAspect;
import com.nhnacademy.marketgg.server.aop.UuidAspect;
import com.nhnacademy.marketgg.server.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.service.CartService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Import({
    RoleCheckAspect.class,
    AuthInjectAspect.class,
    UuidAspect.class
})
class CartControllerTest {

    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CartService cartService;

    String baseUri = "/shop/v1/cart";

    String uuid = "UUID";
    Long productId = 1L;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                                 .alwaysDo(print())
                                 .build();
    }

    @Test
    @DisplayName("회원이 장바구니에 추가")
    void addProductToCart() throws Exception {
        String roles = mapper.writeValueAsString(Collections.singletonList(Role.ROLE_USER));

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATION, roles);

        ProductToCartRequest request = Dummy.getDummyProductToCartRequest(productId);
        String jsonRequest = mapper.writeValueAsString(request);

        mockMvc.perform(post(baseUri)
                   .headers(headers)
                   .contentType(APPLICATION_JSON)
                   .content(jsonRequest))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.success", equalTo(true)));
    }

    @Test
    @DisplayName("없는 상품을 장바구니에 추가")
    void addProductFail() throws Exception {
        String roles = mapper.writeValueAsString(Collections.singletonList(Role.ROLE_USER));

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATION, roles);

        ProductToCartRequest request = Dummy.getDummyProductToCartRequest(productId);
        String jsonRequest = mapper.writeValueAsString(request);

        willThrow(ProductNotFoundException.class).given(cartService).addProduct(anyString(), any(request.getClass()));

        mockMvc.perform(post(baseUri)
                   .headers(headers)
                   .contentType(APPLICATION_JSON)
                   .content(jsonRequest))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.success", equalTo(false)));
    }

    @Test
    @DisplayName("장바구니 조회")
    void testRetrieveCart() throws Exception {
        String roles = mapper.writeValueAsString(Collections.singletonList(Role.ROLE_USER));

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATION, roles);

        given(cartService.retrieveCarts(uuid)).willReturn(new ArrayList<>());

        mockMvc.perform(get(baseUri).headers(headers))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success", equalTo(true)));
    }

    @Test
    @DisplayName("잘못된 회원의 장바구니 조회")
    void testRetrieveCartFail() throws Exception {

        given(cartService.retrieveCarts(uuid)).willReturn(new ArrayList<>());

        mockMvc.perform(get(baseUri))
               .andExpect(status().isUnauthorized())
               .andExpect(jsonPath("$.success", equalTo(false)));
    }

    @Test
    @DisplayName("장바구니 상품 수량 변경")
    void testUpdateProductInCartAmount() throws Exception {
        String roles = mapper.writeValueAsString(Collections.singletonList(Role.ROLE_USER));

        ProductToCartRequest request = Dummy.getDummyProductToCartRequest(productId);
        String jsonRequest = mapper.writeValueAsString(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATION, roles);

        willDoNothing().given(cartService).updateAmount(anyString(), any(request.getClass()));

        mockMvc.perform(patch(baseUri)
                   .headers(headers)
                   .contentType(APPLICATION_JSON)
                   .content(jsonRequest))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success", equalTo(true)));
    }

    @Test
    @DisplayName("장바구니 품목 삭제")
    void testDeleteProducts() throws Exception {
        String roles = mapper.writeValueAsString(Collections.singletonList(Role.ROLE_USER));

        List<Long> productIds = LongStream.rangeClosed(1, 10)
                                          .boxed()
                                          .collect(toList());
        String jsonRequest = mapper.writeValueAsString(productIds);

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATION, roles);


        willDoNothing().given(cartService).deleteProducts(anyString(), anyList());

        mockMvc.perform(delete(baseUri)
                   .headers(headers)
                   .contentType(APPLICATION_JSON)
                   .content(jsonRequest))
               .andExpect(status().isNoContent())
               .andExpect(jsonPath("$.success", equalTo(true)));
    }

}