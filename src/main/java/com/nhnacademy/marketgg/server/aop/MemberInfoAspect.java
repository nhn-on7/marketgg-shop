package com.nhnacademy.marketgg.server.aop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.elasticsearch.monitor.os.OsStats;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Slf4j
@Aspect
@Order(30)
@Component
@RequiredArgsConstructor
public class MemberInfoAspect {

    private final MemberRepository memberRepository;
    private final ObjectMapper mapper;

    @Around("@within(restController) && execution(* *.*(.., com.nhnacademy.marketgg.server.dto.info.MemberInfo, ..))")
    public Object getMemberInfo(ProceedingJoinPoint pjp, RestController restController) throws Throwable {
        log.info("Method: {}", pjp.getSignature().getName());

        HttpServletRequest request = AspectUtils.getRequest();
        String uuid = request.getHeader(AspectUtils.AUTH_ID);
        String roleHeader = request.getHeader(AspectUtils.WWW_AUTHENTICATE);

        MemberInfo memberInfo = new MemberInfo();
        if (!(Objects.isNull(uuid) || Objects.isNull(roleHeader))) {
            List<String> roles = mapper.readValue(roleHeader, new TypeReference<>() {
            });

            memberInfo = memberRepository.findMemberInfoByUuid(uuid)
                                         .orElseThrow(MemberNotFoundException::new);

            memberInfo.addRoles(roles);
        }

        Object[] args = pjp.getArgs();

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof MemberInfo) {
                args[i] = memberInfo;
            }
        }

        return pjp.proceed(args);
    }

}
