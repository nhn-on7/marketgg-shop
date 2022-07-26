package com.nhnacademy.marketgg.server;

import com.nhnacademy.marketgg.server.elastic.repository.EsRepository;
import com.nhnacademy.marketgg.server.repository.JpaRepositoryMarker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackageClasses = { EsRepository.class })
@EnableJpaRepositories(basePackageClasses = { JpaRepositoryMarker.class })
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
