package com.nhnacademy.marketgg.server.config;

import com.nhnacademy.marketgg.server.filter.AdminFilter;
import com.nhnacademy.marketgg.server.filter.SecurityFilter;
import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Web Configuration 을 설정할 수 있습니다.
 *
 * @version 1.0.0
 */
@Configuration
public class WebConfig {

    /**
     * RestTemplate 을 원하는 값으로 설정 후 반환합니다.
     *
     * @param builder - RestTemplate 의 설정을 변경할 수 있는 Builder 객체입니다.
     * @return 원하는 값으로 설정한 RestTemplate 객체를 반환합니다.
     * @since 1.0.0
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setReadTimeout(Duration.ofSeconds(10L))
                .setConnectTimeout(Duration.ofSeconds(5L))
                .build();
    }

    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter() {
        FilterRegistrationBean<AdminFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new AdminFilter());
        filter.setOrder(100);
        filter.addUrlPatterns("/admin/*");

        return filter;
    }

    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> utf8CharacterEncodingFilter() {
        FilterRegistrationBean<CharacterEncodingFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new CharacterEncodingFilter("UTF-8", true));
        filter.setOrder(10);
        filter.addUrlPatterns("/*");

        return filter;
    }

    @Profile("prod")
    @Bean
    public FilterRegistrationBean<SecurityFilter> securityFilterFilterRegistrationBean() {
        FilterRegistrationBean<SecurityFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new SecurityFilter());
        filter.setOrder(1);
        filter.addUrlPatterns("/*");

        return filter;
    }

}
