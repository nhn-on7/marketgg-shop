package com.nhnacademy.marketgg.server.aop;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.GET;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.AuthInfo;
import com.nhnacademy.marketgg.server.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.server.dto.response.common.ErrorEntity;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthInjectAspect {

    @Value("${gateway.origin}")
    private String gateway;

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    @Around("execution(* com.nhnacademy.marketgg.server.controller.*.*(.., @com.nhnacademy.marketgg.server.annotation.Auth (*), ..))")
    public Object authInject(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Method: {}", pjp.getSignature().getName());

        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // String jwt = request.getHeader(AUTHORIZATION);
        String jwt =
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTdmZGVkYi1mYjdiLTQ1NGItYWMzMC0yMWU2YjMxOTIzOTAiLCJBVVRIT1JJVElFUyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjU4NjcyMjY1LCJleHAiOjE2NTg2NzQwNjV9.BYqN5OxzJq7lzjnzT2o2KYvv_NGqT8AUPjrm2fCM4Z0";
        // String uuid = request.getHeader("WWW-Authentication");
        String uuid = "1234";

        if (Objects.isNull(jwt) || Objects.isNull(uuid)) {
            throw new IllegalArgumentException();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(AUTHORIZATION, jwt);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> exchange =
            restTemplate.exchange(gateway + "/auth/info", GET, httpEntity, String.class);

        AuthInfo authInfo = validCheck(exchange);
        authInfo.setUuid(uuid);

        Object[] args = Arrays.stream(pjp.getArgs())
                              .map(arg -> {
                                  if (arg instanceof AuthInfo) {
                                      arg = authInfo;
                                  }
                                  return arg;
                              }).toArray();

        return pjp.proceed(args);
    }

    private AuthInfo validCheck(ResponseEntity<String> response)
        throws JsonProcessingException {

        System.out.println("response.getBody() = " + response.getBody());
        log.info("http status: {}", response.getStatusCode());
        if (response.getStatusCode().is4xxClientError()) {
            ErrorEntity error =
                mapper.readValue(response.getBody(), ErrorEntity.class);
            throw new IllegalArgumentException(error.getMessage());
        }

        SingleResponse<AuthInfo> authInfo
            = mapper.readValue(response.getBody(), new TypeReference<>() {
        });

        return authInfo.getData();
    }

}
