package com.nhnacademy.marketgg.server.aop;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.GET;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.AuthInfo;
import com.nhnacademy.marketgg.server.dto.response.common.ErrorEntity;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.exception.auth.UnAuthenticException;
import java.util.Arrays;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Controller 클래스에서 Auth Server 의 회원 요청 시 파라미터로 쉽게 전달받을 수 있는 AOP.
 *
 * @version 1.0.0
 */
@Slf4j
@Aspect
@Order(40)
@Component
@RequiredArgsConstructor
public class AuthInjectAspect {

    @Value("${gg.gateway.origin}")
    private String gateway;

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    /**
     * 회원 정보를 요청하는 Aspect.
     *
     * @param pjp - 메서드 원본의 정보를 가지고있는 객체입니다.
     * @return 메서드 정보
     * @throws Throwable 메서드를 실행시킬 때 발생할 수 있는 예외입니다.
     */
    @Around("execution(* com.nhnacademy.marketgg.server.controller.*.*(.., com.nhnacademy.marketgg.server.dto.AuthInfo, ..))")
    public Object authInject(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Method: {}", pjp.getSignature().getName());

        HttpServletRequest request = AspectUtils.getRequest();

        String jwt = request.getHeader(AUTHORIZATION);
        String uuid = request.getHeader(AspectUtils.AUTH_ID);

        if (isInvalidAuth(jwt, uuid)) {
            throw new UnAuthenticException();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(AUTHORIZATION, jwt);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> exchange =
            restTemplate.exchange(gateway + "/auth/info", GET, httpEntity, String.class);

        AuthInfo authInfo = validCheck(exchange);

        Object[] args = Arrays.stream(pjp.getArgs())
                              .map(arg -> {
                                  if (arg instanceof AuthInfo) {
                                      arg = authInfo;
                                  }
                                  return arg;
                              }).toArray();

        return pjp.proceed(args);
    }

    private boolean isInvalidAuth(String jwt, String uuid) {
        return jwt.isBlank() || uuid.isBlank();
    }

    private AuthInfo validCheck(ResponseEntity<String> response)
        throws JsonProcessingException {

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
