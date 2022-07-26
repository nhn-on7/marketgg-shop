package com.nhnacademy.marketgg.server.aop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.annotation.RoleCheck;
import com.nhnacademy.marketgg.server.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.server.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.server.util.JwtUtils;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
     * @param jp        - 메서드의 상태정보를 가지고있습니다.
     * @param roleCheck - Custom Annotation 으로 필요한 권한 정보를 가지고있습니다.
     * @throws IOException            - JSON 역 직렬화 시 발생할 수 있는 예외입니다.
     * @throws IllegalAccessException - 권한이 불충분할 시 발생할 수 있는 예외입니다.
     */
    @Before(value = "@annotation(roleCheck)")
    public void checkRole(JoinPoint jp, RoleCheck roleCheck)
        throws IOException, IllegalAccessException {

        log.info("Role Check AOP");

        HttpServletRequest request = AspectUtils.getRequest();

        MethodSignature signature = (MethodSignature) jp.getSignature();

        String roleHeader = request.getHeader(JwtUtils.WWW_AUTHENTICATION);
        String uuid = request.getHeader(JwtUtils.AUTH_ID);

        if (Objects.isNull(roleHeader) && Objects.isNull(uuid)) {
            if (Objects.equals(roleCheck.accessLevel(), Role.LOGIN)) {
                throw new UnAuthenticException(signature.getName());
            }
            throw new UnAuthorizationException(signature.getName(), "Empty");
        }

        // 권한 목록은 Gateway 에서 JSON List 타입으로 매핑해서 Http Header 로 전달함.
        List<String> roles = mapper.readValue(roleHeader, new TypeReference<>() {
        });
        log.info("roles = {}", roles.toString());

        if (Objects.equals(roleCheck.accessLevel(), Role.ROLE_USER)) {
            this.isUser(signature.getMethod(), roles);
            return;
        }

        if (Objects.equals(roleCheck.accessLevel(), Role.ROLE_ADMIN)) {
            this.isAdmin(signature.getMethod(), roles);
        }

    }

    private void isUser(Method method, List<String> roles) throws UnAuthorizationException {
        if (!(roles.contains(Role.ROLE_USER.name()) || roles.contains(Role.ROLE_ADMIN.name()))) {
            throw new UnAuthorizationException(method.getName(), roles.toString());
        }
    }

    private void isAdmin(Method method, List<String> roles) throws UnAuthorizationException {
        if (!roles.contains(Role.ROLE_ADMIN.name())) {
            throw new UnAuthorizationException(method.getName(), roles.toString());
        }
    }

}
