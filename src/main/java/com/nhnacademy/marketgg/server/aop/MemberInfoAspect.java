package com.nhnacademy.marketgg.server.aop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(30)
@Component
@RequiredArgsConstructor
public class MemberInfoAspect {

    private final MemberRepository memberRepository;
    private final ObjectMapper mapper;

    @Around("execution(* com.nhnacademy.marketgg.server.controller..*.*(.., com.nhnacademy.marketgg.server.dto.MemberInfo, ..))")
    public Object getMemberInfo(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Method: {}", pjp.getSignature().getName());

        HttpServletRequest request = AspectUtils.getRequest();
        String uuid = request.getHeader(AspectUtils.AUTH_ID);
        String roleHeader = request.getHeader(AspectUtils.WWW_AUTHENTICATE);

        List<String> roles = mapper.readValue(roleHeader, new TypeReference<>() {
        });

        MemberInfo memberInfo = memberRepository.findMemberInfoByUuid(uuid)
                                                .orElseThrow(MemberNotFoundException::new);

        memberInfo.addRoles(roles);

        Object[] args = Arrays.stream(pjp.getArgs())
                              .map(arg -> {
                                  if (arg instanceof MemberInfo) {
                                      arg = memberInfo;
                                  }
                                  return arg;
                              }).toArray();

        return pjp.proceed(args);
    }

}
