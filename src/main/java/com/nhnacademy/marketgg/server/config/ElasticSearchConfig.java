package com.nhnacademy.marketgg.server.config;

import com.nhnacademy.marketgg.server.elasticrepository.EsRepositoryMarker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Es Server 와 Spring boot 프로젝트의 연동을 위한 설정입니다.
 * JPA Repository 와의 충돌을 방지하기위해 ES Repository 의 패키지 경로를 선언해줍니다.
 *
 *
 * @version 1.0.0
 */
@Slf4j
@EnableElasticsearchRepositories(basePackageClasses = { EsRepositoryMarker.class })
@Configuration
public class ElasticSearchConfig {

    /**
     *
     *
     * @since 1.0.0
     */
    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private Integer port;

}
