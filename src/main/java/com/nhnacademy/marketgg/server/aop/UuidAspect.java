package com.nhnacademy.marketgg.server.aop;

import com.nhnacademy.marketgg.server.annotation.UUID;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * 컨트롤러에서 사용자의 UUID 를 전달받을 수 있다.
 *
 * @version 1.0.0
 */
@Slf4j
@Aspect
@Order(20)
@Component
public class UuidAspect {

    @Around("execution(* com.nhnacademy.marketgg.server.controller.*.*(.., com.nhnacademy.marketgg.server.annotation.UUID ,..))")
    public Object getUuid(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Method: {}", pjp.getSignature().getName());

        HttpServletRequest request = AspectUtils.getRequest();
        String uuid = request.getHeader(AspectUtils.AUTH_ID);

        Parameter[] parameters = ((MethodSignature) pjp.getSignature()).getMethod().getParameters();
        Object[] args = pjp.getArgs();

        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            if (Objects.nonNull(param.getAnnotation(UUID.class))) {
                args[i] = uuid;
                break;
            }
        }

        return pjp.proceed(args);
    }

}

