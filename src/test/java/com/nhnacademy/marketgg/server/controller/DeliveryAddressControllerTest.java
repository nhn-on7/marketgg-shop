package com.nhnacademy.marketgg.server.controller;

import static com.nhnacademy.marketgg.server.aop.AspectUtils.AUTH_ID;
import static com.nhnacademy.marketgg.server.aop.AspectUtils.WWW_AUTHENTICATE;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.aop.AuthInfoAspect;
import com.nhnacademy.marketgg.server.aop.MemberInfoAspect;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.deliveryaddress.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.repository.deliveryaddress.DeliveryAddressRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.service.deliveryaddress.DeliveryAddressService;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
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

@Transactional
@SpringBootTest
@ActiveProfiles({ "testdb", "common", "local" })
@Import({
    AuthInfoAspect.class,
    MemberInfoAspect.class
})
class DeliveryAddressControllerTest {

    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    DeliveryAddressService deliveryAddressService;

    @MockBean
    DeliveryAddressRepository deliveryAddressRepository;

    String baseUri = "/members/delivery-address";

    @MockBean
    MemberRepository memberRepository;

    MemberInfo member;
    HttpHeaders headers;
    String uuid;
    Long memberId = 1L;
    Long deliveryAddressNo = 1L;

    @BeforeEach
    void setUp(WebApplicationContext wac) throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
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
    @DisplayName("배송지 추가")
    void testCreateDeliveryAddress() throws Exception {
        String roles = mapper.writeValueAsString(Collections.singletonList(Role.ROLE_USER));

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATE, roles);

        DeliveryAddressCreateRequest request = Dummy.getDeliveryAddressCreateRequest();
        String jsonRequest = mapper.writeValueAsString(request);

        mockMvc.perform(post(baseUri).headers(headers)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(jsonRequest))
               .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("배송지 삭제")
    void testDeleteDeliveryAddress() throws Exception {
        String roles = mapper.writeValueAsString(Collections.singletonList(Role.ROLE_USER));

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATE, roles);

        mockMvc.perform(delete(baseUri + "/{deliveryNo}", deliveryAddressNo)
                   .headers(headers))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("배송지 전체 리스트 조회")
    void testRetrieveDeliveryAddresses() throws Exception {
        String roles = mapper.writeValueAsString(Collections.singletonList(Role.ROLE_USER));

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_ID, uuid);
        headers.set(WWW_AUTHENTICATE, roles);

        mockMvc.perform(get("/members/delivery-addresses")
                   .headers(headers))
               .andExpect(status().isOk());
    }

}
