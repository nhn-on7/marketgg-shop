package com.nhnacademy.marketgg.server.aop;

import com.nhnacademy.marketgg.server.annotation.UUID;
import java.lang.reflect.Parameter;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 컨트롤러에서 사용자의 UUID 를 전달받을 수 있습니다.
 *
 * @version 1.0.0
 */
@Slf4j
@Aspect
@Order(20)
@Component
public class UuidAspect {

    /**
     * 컨트롤러 클래스에 UUID 를 전달합니다.
     *
     * @param pjp - 메서드 원본 실행시킬 수 있는 객체입니다.
     * @return 메서드를 실행시킵니다.
     * @throws Throwable 메서드를 실행시킬 때 발생할 수 있는 예외입니다.
     */

    @Around("execution(* com.nhnacademy.marketgg.server.controller.*.*(.., @com.nhnacademy.marketgg.server.annotation.UUID (*) ,..))")
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

