package com.nhnacademy.marketgg.server.config;

import com.nhnacademy.marketgg.server.elastic.repository.ElasticRepositoryMarker;
import com.nhnacademy.marketgg.server.repository.JpaRepositoryMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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

}
