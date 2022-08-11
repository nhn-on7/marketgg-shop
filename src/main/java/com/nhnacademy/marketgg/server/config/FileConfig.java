package com.nhnacademy.marketgg.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.service.storage.LocalStorageService;
import com.nhnacademy.marketgg.server.service.storage.NhnStorageService;
import com.nhnacademy.marketgg.server.service.storage.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Configuration을 설정합니다.
 *
 * @author 조현진
 */
@Configuration
public class FileConfig {

    @Bean
    public StorageService localStorageService() {
        return new LocalStorageService();
    }

    @Bean
    public StorageService nhnStorageService() {
        return new NhnStorageService(new RestTemplate(), new ObjectMapper());
    }

}
