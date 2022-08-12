package com.nhnacademy.marketgg.server.config;

import com.nhnacademy.marketgg.server.interceptor.AdminInterceptor;
import com.nhnacademy.marketgg.server.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**");
        registry.addInterceptor(new AuthInterceptor());
    }

}
