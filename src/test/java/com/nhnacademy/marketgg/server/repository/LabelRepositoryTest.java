package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.dto.request.label.LabelCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.label.LabelRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.repository.label.LabelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LabelRepositoryTest {

    @Autowired
    private LabelRepository labelRepository;

    private static LabelCreateRequest labelCreateRequest;

    @BeforeAll
    static void beforeAll() {
        labelCreateRequest = new LabelCreateRequest();

        ReflectionTestUtils.setField(labelCreateRequest, "labelNo", 1L);
        ReflectionTestUtils.setField(labelCreateRequest, "name", "labelName");
    }

    @Test
    @DisplayName("모든 라벨 목록 조회")
    void testAllLabels() {
        Label label = new Label(labelCreateRequest);

        labelRepository.save(label);

        List<LabelRetrieveResponse> results = labelRepository.findAllLabels();

        assertThat(results).hasSize(1);
    }

}
