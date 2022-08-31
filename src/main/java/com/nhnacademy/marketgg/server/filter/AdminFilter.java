package com.nhnacademy.marketgg.server.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Role;
import com.nhnacademy.marketgg.server.aop.AspectUtils;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String roleHeader = request.getHeader(AspectUtils.WWW_AUTHENTICATE);
        String uuid = request.getHeader(AspectUtils.AUTH_ID);

        if (isInvalidHeader(roleHeader, uuid)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "로그인 후 접근 가능합니다.");
        }

        // 권한 목록은 Gateway 에서 JSON List 타입으로 매핑해서 Http Header 로 전달함.
        List<String> roles = new ObjectMapper().readValue(roleHeader, new TypeReference<>() {
        });

        log.info("roles = {}", roles.toString());

        if (roles.contains(Role.ROLE_ADMIN.name())) {
            filterChain.doFilter(request, response);
            return;
        }

        response.sendError(HttpStatus.FORBIDDEN.value(), "접근 권한이 없습니다.");
    }

    private boolean isInvalidHeader(String roleHeader, String uuid) {
        return (Objects.isNull(roleHeader) || Objects.isNull(uuid))
                || (roleHeader.isBlank() || uuid.isBlank());
    }

}
