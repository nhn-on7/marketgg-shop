package com.nhnacademy.marketgg.server.config;

import com.nhnacademy.marketgg.server.elastic.repository.ElasticRepositoryMarker;
import com.nhnacademy.marketgg.server.repository.JpaRepositoryMarker;
import com.nhnacademy.marketgg.server.util.KoreanToEnglishTranslator;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 엘라스틱 서치의 설정과 기능을 주입해주는 Configuration 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE,
    classes = {ElasticRepositoryMarker.class}),
    basePackageClasses = {JpaRepositoryMarker.class})
@EnableElasticsearchRepositories(excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE,
    classes = {JpaRepositoryMarker.class}),
    basePackageClasses = {ElasticRepositoryMarker.class})
@Configuration
public class ElasticConfig {

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

    /**
     * JSON 을 파싱해주는 객체를 반환해줍니다.
     *
     * @return JSON 파싱을 지원하는 객체를 반환합니다.
     */
    @Bean
    public JSONParser parser() {
        return new JSONParser();
    }

}
