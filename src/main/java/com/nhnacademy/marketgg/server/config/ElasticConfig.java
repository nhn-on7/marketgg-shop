package com.nhnacademy.marketgg.server.config;

import com.nhnacademy.marketgg.server.elastic.repository.ElasticRepositoryMarker;
import com.nhnacademy.marketgg.server.repository.JpaRepositoryMarker;
import com.nhnacademy.marketgg.server.utils.KoreanToEnglishTranslator;
import java.time.Duration;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = { ElasticRepositoryMarker.class }),
                       basePackageClasses = { JpaRepositoryMarker.class })
@EnableElasticsearchRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = { JpaRepositoryMarker.class }),
                                 basePackageClasses = { ElasticRepositoryMarker.class })
@Configuration
public class ElasticConfig {

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

    /**
     * 한국어 오타 배열을 영어 알파벳으로 변환해주는 객체를 반환합니다.
     *
     * @return 한국어 오타 배열을 영어 알파벳으로 변환시켜주는 객체를 반환합니다.
     * @since 1.0.0
     */
    @Bean
    public KoreanToEnglishTranslator translator() {
        return new KoreanToEnglishTranslator();
    }

    @Bean
    public JSONParser parser() {
        return new JSONParser();
    }

}
