package com.nhnacademy.marketgg.server.config;

import com.nhnacademy.marketgg.server.elastic.repository.EsBoardRepository;
import com.nhnacademy.marketgg.server.elastic.repository.EsProductRepository;
import com.nhnacademy.marketgg.server.elastic.repository.EsRepository;
import com.nhnacademy.marketgg.server.repository.JpaRepositoryMarker;
import com.nhnacademy.marketgg.server.repository.dib.DibRepository;
import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

/**
 * Web Configuration 을 설정할 수 있습니다.
 *
 * @version 1.0.0
 */

@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = { EsRepository.class }),
                       basePackageClasses = { JpaRepositoryMarker.class })
@EnableElasticsearchRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = { JpaRepositoryMarker.class }),
                       basePackageClasses = { EsRepository.class })
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

}
