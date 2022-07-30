package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.marketgg.server.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.server.repository.categorization.CategorizationRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultCategorizationServiceTest {

    @InjectMocks
    DefaultCategorizationService categorizationService;

    @Mock
    CategorizationRepository categorizationRepository;

    @Test
    @DisplayName("카테고리 분류표 조회")
    void retrieveCategorizations() {
        given(categorizationRepository.findAllCategorization()).willReturn(
                List.of(new CategorizationRetrieveResponse("001", "hello")));

        List<CategorizationRetrieveResponse> responses =
                categorizationService.retrieveCategorizations();

        assertThat(responses.get(0).getName()).isEqualTo("hello");
    }

}