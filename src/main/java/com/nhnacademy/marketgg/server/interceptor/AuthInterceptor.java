package com.nhnacademy.marketgg.server.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Auth;
import com.nhnacademy.marketgg.server.aop.AspectUtils;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        log.info("Path = {}", request.getRequestURI());

        HandlerMethod method = (HandlerMethod) handler;

        Auth authAnnotation = method.getMethodAnnotation(Auth.class);

        if (Objects.isNull(authAnnotation)) {
            authAnnotation = method.getBean().getClass().getAnnotation(Auth.class);
        }

        if (Objects.nonNull(authAnnotation)) {
            ObjectMapper mapper = new ObjectMapper();

            String uuid = request.getHeader(AspectUtils.AUTH_ID);
            List<String> roles = mapper.readValue(request.getHeader(AspectUtils.WWW_AUTHENTICATE),
                new TypeReference<>() {
                });

            if (Objects.isNull(uuid) || Objects.isNull(roles) || roles.isEmpty()) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        }

        return true;
    }
}
