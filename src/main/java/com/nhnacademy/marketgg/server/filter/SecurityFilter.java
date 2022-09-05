package com.nhnacademy.marketgg.server.filter;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

@Slf4j
@RequiredArgsConstructor
public class SecurityFilter implements Filter {

    private final String gateway;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {

        String gatewayUrl = gateway.substring(7);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String xForwardedFor = request.getHeader("X-Forwarded-For");

        log.info("gateway: {}", gatewayUrl);
        log.info("X-Forwarded-For: {}", xForwardedFor);

        if (Objects.nonNull(xForwardedFor)) {
            log.warn("Origin: {}", xForwardedFor);
            log.warn("URI: {}", request.getRequestURI());
            if (!Objects.equals(xForwardedFor, gatewayUrl)) {
                response.sendError(HttpStatus.FORBIDDEN.value());
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
