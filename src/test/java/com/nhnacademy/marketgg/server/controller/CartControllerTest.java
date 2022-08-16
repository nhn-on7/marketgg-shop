package com.nhnacademy.marketgg.server.controller;

import static com.nhnacademy.marketgg.server.aop.AspectUtils.AUTH_ID;
import static com.nhnacademy.marketgg.server.aop.AspectUtils.WWW_AUTHENTICATE;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.aop.AuthInjectAspect;
import com.nhnacademy.marketgg.server.aop.MemberInfoAspect;
import com.nhnacademy.marketgg.server.controller.advice.AuthControllerAdvice;
import com.nhnacademy.marketgg.server.controller.advice.GlobalControllerAdvice;
import com.nhnacademy.marketgg.server.controller.advice.MemberControllerAdvice;
import com.nhnacademy.marketgg.server.controller.cart.CartController;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.product.ProductToCartRequest;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.exception.product.ProductNotFoundException;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.cart.CartProductService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles({ "testdb", "common", "local" })
@Import({
        AuthInjectAspect.class,
        MemberInfoAspect.class
})
class CartControllerTest {

    MockMvc mockMvc;

    @Autowired
    CartController controller;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    GlobalControllerAdvice globalControllerAdvice;

    @Autowired
    AuthControllerAdvice authControllerAdvice;

    @Autowired
    MemberControllerAdvice memberControllerAdvice;

    @MockBean
    CartProductService cartProductService;

    @MockBean
    MemberRepository memberRepository;

    String baseUri = "/cart";

    MemberInfo member;
    HttpHeaders headers;

    String uuid;
    Long memberId = 1L;
    Long productId = 1L;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                                 .setControllerAdvice(globalControllerAdvice, authControllerAdvice,
                                                      memberControllerAdvice)
                                 .alwaysDo(print())
                                 .build();

        uuid = UUID.randomUUID().toString();
        MemberInfo memberInfo = Dummy.getDummyMemberInfo(memberId, new Cart());
        given(memberRepository.findMemberInfoByUuid(uuid)).willReturn(Optional.of(memberInfo));

        String roles = mapper.writeValueAsString(Collections.singletonList(Role.ROLE_USER));
        headers = new HttpHeaders();
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATE, roles);
    }

    @Test
    @DisplayName("회원이 장바구니에 추가")
    void addProductToCart() throws Exception {
        String roles = mapper.writeValueAsString(Collections.singletonList(Role.ROLE_USER));

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATE, roles);

        ProductToCartRequest request = Dummy.getDummyProductToCartRequest(productId);
        String jsonRequest = mapper.writeValueAsString(request);

        mockMvc.perform(post(baseUri)
                                .headers(headers)
                                .contentType(APPLICATION_JSON)
                                .content(jsonRequest))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.success", equalTo(true)));

        then(memberRepository).should(times(1)).findMemberInfoByUuid(uuid);
    }

    @Test
    @DisplayName("없는 상품을 장바구니에 추가")
    void addProductFail() throws Exception {
        ProductToCartRequest request = Dummy.getDummyProductToCartRequest(productId);
        String jsonRequest = mapper.writeValueAsString(request);

        willThrow(ProductNotFoundException.class)
                .given(cartProductService).addProduct(any(MemberInfo.class), any(request.getClass()));

        mockMvc.perform(post(baseUri)
                                .headers(headers)
                                .contentType(APPLICATION_JSON)
                                .content(jsonRequest))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.success", equalTo(false)));

        then(cartProductService).should(times(1)).addProduct(any(MemberInfo.class), any(request.getClass()));
    }

    @Test
    @DisplayName("장바구니 조회")
    void testRetrieveCart() throws Exception {
        given(cartProductService.retrieveCarts(member)).willReturn(new ArrayList<>());

        mockMvc.perform(get(baseUri).headers(headers))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success", equalTo(true)));

        then(cartProductService).should(times(0)).retrieveCarts(member);
    }

    @Test
    @DisplayName("잘못된 회원의 장바구니 조회")
    void testRetrieveCartFail() throws Exception {
        given(cartProductService.retrieveCarts(member)).willReturn(new ArrayList<>());

        mockMvc.perform(get(baseUri))
               .andExpect(status().isUnauthorized())
               .andExpect(jsonPath("$.success", equalTo(false)));

        then(cartProductService).should(times(0)).retrieveCarts(member);
    }

    @Test
    @DisplayName("장바구니 상품 수량 변경")
    void testUpdateProductInCartAmount() throws Exception {
        ProductToCartRequest request = Dummy.getDummyProductToCartRequest(productId);
        String jsonRequest = mapper.writeValueAsString(request);

        willDoNothing().given(cartProductService).updateAmount(any(MemberInfo.class), any(request.getClass()));

        mockMvc.perform(patch(baseUri)
                                .headers(headers)
                                .contentType(APPLICATION_JSON)
                                .content(jsonRequest))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success", equalTo(true)));

        then(cartProductService).should(times(1)).updateAmount(any(MemberInfo.class), any(request.getClass()));
    }

    @Test
    @DisplayName("장바구니 상품 수량 잘못된 값으로 변경")
    void testUpdateProductInCartAmountFail() throws Exception {
        ProductToCartRequest request = Dummy.getDummyProductToCartRequest(productId, 9999);
        String jsonRequest = mapper.writeValueAsString(request);

        mockMvc.perform(patch(baseUri)
                                .headers(headers)
                                .contentType(APPLICATION_JSON)
                                .content(jsonRequest))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.success", equalTo(false)));
    }

    @Test
    @DisplayName("장바구니 품목 삭제")
    void testDeleteProducts() throws Exception {
        List<Long> productIds = LongStream.rangeClosed(1, 10)
                                          .boxed()
                                          .collect(toList());

        String jsonRequest = mapper.writeValueAsString(productIds);

        willDoNothing().given(cartProductService).deleteProducts(any(MemberInfo.class), anyList());

        mockMvc.perform(delete(baseUri)
                                .headers(headers)
                                .contentType(APPLICATION_JSON)
                                .content(jsonRequest))
               .andExpect(status().isNoContent())
               .andExpect(jsonPath("$.success", equalTo(true)));

        then(cartProductService).should(times(1)).deleteProducts(any(MemberInfo.class), anyList());
    }

}
