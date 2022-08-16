package com.nhnacademy.marketgg.server.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.marketgg.server.dto.response.category.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.server.repository.categorization.CategorizationRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategorizationRepositoryTest {

    @Autowired
    private CategorizationRepository categorizationRepository;

    @Test
    @DisplayName("모든 카테고리 분류표 목록 조회")
    void testRetrieveCategorization() {
        List<CategorizationRetrieveResponse> result = categorizationRepository.findAllCategorization();

        assertThat(result).isEmpty();
    }

}
