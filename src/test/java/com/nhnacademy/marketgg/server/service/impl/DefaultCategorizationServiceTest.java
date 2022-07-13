package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.nhnacademy.marketgg.server.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.server.repository.CategorizationRepository;
import com.nhnacademy.marketgg.server.service.CategorizationService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DefaultCategorizationServiceTest {

    @Autowired
    CategorizationService categorizationService;

    @MockBean
    CategorizationRepository categorizationRepository;

    @Test
    @DisplayName("카테고리 분류표 조회")
    void retrieveCategorizations() {
        when(categorizationRepository.findAllCategorization()).thenReturn(List.of(
                new CategorizationRetrieveResponse() {
                    @Override
                    public String getCategorizationCode() {
                        return "001";
                    }

                    @Override
                    public String getName() {
                        return "hello";
                    }
                }));

        List<CategorizationRetrieveResponse> responses = categorizationService.retrieveCategorizations();

        assertThat(responses.get(0).getName()).isEqualTo("hello");
    }

}