package com.nhnacademy.marketgg.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Es Server 와 Spring boot 프로젝트의 연동을 위한 설정입니다.
 * JPA Repository 와의 충돌을 방지하기위해 ES Repository 의 패키지 경로를 선언해줍니다.
 *
 * @value host - Es Server 의 HostName 입니다.
 * @value port - Es Server 의 Port 입니다.
 *
 * @version 1.0.0
 */
@Slf4j
@EnableElasticsearchRepositories(basePackages = { "com.nhnacademy.marketgg.server.elasticrepository" })
@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private Integer port;

}
