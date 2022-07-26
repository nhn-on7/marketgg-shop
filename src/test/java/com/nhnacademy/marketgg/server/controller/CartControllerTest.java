package com.nhnacademy.marketgg.server.controller;

import static com.nhnacademy.marketgg.server.util.JwtUtils.AUTH_ID;
import static com.nhnacademy.marketgg.server.util.JwtUtils.WWW_AUTHENTICATION;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
import java.util.Collections;
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

    // @Autowired
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

}