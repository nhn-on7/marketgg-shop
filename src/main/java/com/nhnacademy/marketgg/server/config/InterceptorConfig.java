package com.nhnacademy.marketgg.server.config;

import com.nhnacademy.marketgg.server.interceptor.AdminInterceptor;
import com.nhnacademy.marketgg.server.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 컨트롤러의 요청 및 응답을 가로채서 관리자 및 인증 인터셉터 기능을 수행하고, API 문서화(Swagger)를 위한 자원을 등록합니다.
 *
 * @author 윤동열
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor())
                .excludePathPatterns("classpath:/resources/**")
                .addPathPatterns("/admin/**");
        registry.addInterceptor(new AuthInterceptor())
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui/**", "/api/**", "classpath:/resources/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("**/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
