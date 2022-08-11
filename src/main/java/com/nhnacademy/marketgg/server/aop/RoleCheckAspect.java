package com.nhnacademy.marketgg.server.aop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.server.exception.auth.UnAuthorizationException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Annotation 으로 권한 처리하는 클래스
 *
 * @version 1.0.0
 */
@Slf4j
@Aspect
@Order(10)
@Component
@RequiredArgsConstructor
public class RoleCheckAspect {

    private final ObjectMapper mapper;

    /**
     * 메서드 진입 시 필요한 권한을 체크합니다.
     *
     * @param roleCheck - Custom Annotation 으로 필요한 권한 정보를 가지고있습니다.
     * @throws IOException            - JSON 역 직렬화 시 발생할 수 있는 예외입니다.
     * @throws IllegalAccessException - 권한이 불충분할 시 발생할 수 있는 예외입니다.
     */
    @Before(value = "@annotation(roleCheck) || @within(roleCheck)")
    public void checkRole(JoinPoint jp, RoleCheck roleCheck)
        throws IOException, IllegalAccessException {
        log.info("Method = {}", jp.getSignature().getName());

        log.debug("Role Check AOP");

        HttpServletRequest request = AspectUtils.getRequest();

        String roleHeader = request.getHeader(AspectUtils.WWW_AUTHENTICATE);
        String uuid = request.getHeader(AspectUtils.AUTH_ID);

        if (isInvalidHeader(roleHeader, uuid)) {
            throw new UnAuthenticException();
        }

        // 권한 목록은 Gateway 에서 JSON List 타입으로 매핑해서 Http Header 로 전달함.
        List<String> roles = mapper.readValue(roleHeader, new TypeReference<>() {
        });
        log.info("roles = {}", roles.toString());

        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();

        RoleCheck check = method.getAnnotation(RoleCheck.class);
        log.info("accessLevel = {}", check.accessLevel());

        if (Objects.equals(check.accessLevel(), Role.ROLE_USER)) {
            this.isUser(roles);
            return;
        }

        if (Objects.equals(roleCheck.accessLevel(), Role.ROLE_ADMIN)) {
            this.isAdmin(roles);
        }

    }

    private boolean isInvalidHeader(String roleHeader, String uuid) {
        return (Objects.isNull(roleHeader) || Objects.isNull(uuid))
            || (roleHeader.isBlank() || uuid.isBlank());
    }

    private void isUser(List<String> roles) throws UnAuthorizationException {
        if (!(roles.contains(Role.ROLE_USER.name()) || roles.contains(Role.ROLE_ADMIN.name()))) {
            throw new UnAuthorizationException();
        }
    }

    private void isAdmin(List<String> roles) throws UnAuthorizationException {
        if (!roles.contains(Role.ROLE_ADMIN.name())) {
            throw new UnAuthorizationException();
        }
    }

}
