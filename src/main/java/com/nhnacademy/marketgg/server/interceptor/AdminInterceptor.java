package com.nhnacademy.marketgg.server.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.aop.AspectUtils;
import com.nhnacademy.marketgg.server.exception.auth.UnAuthenticException;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        String roleHeader = request.getHeader(AspectUtils.WWW_AUTHENTICATE);
        String uuid = request.getHeader(AspectUtils.AUTH_ID);

        if (isInvalidHeader(roleHeader, uuid)) {
            throw new UnAuthenticException();
        }

        // 권한 목록은 Gateway 에서 JSON List 타입으로 매핑해서 Http Header 로 전달함.

        List<String> roles = new ObjectMapper().readValue(roleHeader, new TypeReference<>() {});

        log.info("roles = {}", roles.toString());

        if (roles.contains(Role.ROLE_ADMIN.name())) {
            return true;
        }

        response.setStatus(HttpStatus.FORBIDDEN.value());
        return false;
    }

    private boolean isInvalidHeader(String roleHeader, String uuid) {
        return (Objects.isNull(roleHeader) || Objects.isNull(uuid))
            || (roleHeader.isBlank() || uuid.isBlank());
    }

}
