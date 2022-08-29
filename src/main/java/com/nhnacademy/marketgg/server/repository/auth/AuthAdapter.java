package com.nhnacademy.marketgg.server.repository.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.ShopResult;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoRequest;
import com.nhnacademy.marketgg.server.dto.info.MemberInfoResponse;
import com.nhnacademy.marketgg.server.dto.info.MemberNameResponse;
import com.nhnacademy.marketgg.server.dto.request.member.MemberUpdateRequest;
import com.nhnacademy.marketgg.server.dto.request.member.SignupRequest;
import com.nhnacademy.marketgg.server.dto.response.auth.UuidTokenResponse;
import com.nhnacademy.marketgg.server.dto.response.member.SignupResponse;
import com.nhnacademy.marketgg.server.exception.member.MemberInfoNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * auth 서버에서 uuid 목록을 전송해 이름목록을 가져옵니다.
 *
 * @author 박세완
 * @author 민아영
 * @author 김정민
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class AuthAdapter implements AuthRepository {

    @Value("${gg.gateway.origin}")
    private String gateway;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private static final String DEFAULT_AUTH = "/auth/v1/members/info";
    private static final String DEFAULT_SIGNUP = "/auth/v1/members/signup";


    @Override
    public List<MemberNameResponse> getNameListByUuid(final List<String> uuidList) throws JsonProcessingException {

        if (uuidList.isEmpty()) {
            return Collections.emptyList();
        }

        String requestBody = objectMapper.writeValueAsString(uuidList);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, buildHeaders());
        ResponseEntity<ShopResult<List<MemberNameResponse>>> response = restTemplate.exchange(
                gateway + DEFAULT_AUTH + "/names",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public ShopResult<MemberInfoResponse> getMemberInfo(final MemberInfoRequest memberInfoRequest)
            throws JsonProcessingException {
        String requestBody = objectMapper.writeValueAsString(memberInfoRequest);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, buildHeaders());
        ResponseEntity<ShopResult<MemberInfoResponse>> response = restTemplate.exchange(
                gateway + DEFAULT_AUTH + "/person",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }

    @Override
    public ShopResult<SignupResponse> signup(final SignupRequest signUpRequest) throws JsonProcessingException {
        String requestBody = objectMapper.writeValueAsString(signUpRequest);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, buildHeaders());
        ResponseEntity<ShopResult<SignupResponse>> response = restTemplate.exchange(
                gateway + DEFAULT_SIGNUP,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }

    @Override
    public void withdraw(final LocalDateTime withdrawAt) {
        HttpEntity<LocalDateTime> requestEntity = new HttpEntity<>(withdrawAt, buildHeaders());
        restTemplate.exchange(
                gateway + DEFAULT_AUTH,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public UuidTokenResponse update(final MemberUpdateRequest memberUpdateRequest, final String token) {
        HttpHeaders httpHeaders = buildHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, token);

        HttpEntity<MemberUpdateRequest> requestEntity = new HttpEntity<>(memberUpdateRequest, httpHeaders);
        ResponseEntity<ShopResult<UuidTokenResponse>> response = restTemplate.exchange(
                gateway + DEFAULT_AUTH,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(response.getBody()).getData();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

    // memo: 주문뿐 아니라 상품문의에서도 쓰이고 있어서 우선 static 메소드로 만들어놓음
    public static MemberInfoResponse checkResult(final ShopResult<MemberInfoResponse> shopResult) {
        if (shopResult.isSuccess() && Objects.nonNull(shopResult.getData())) {
            return shopResult.getData();
        }
        throw new MemberInfoNotFoundException(shopResult.getError().getMessage());
    }

}
