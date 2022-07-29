package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

import com.nhnacademy.marketgg.server.dto.request.LabelCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.LabelRetrieveResponse;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticProductRepository;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.exception.label.LabelNotFoundException;
import com.nhnacademy.marketgg.server.repository.label.LabelRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultLabelServiceTest {

    @InjectMocks
    DefaultLabelService labelService;

    @Mock
    LabelRepository labelRepository;
    @Mock
    ElasticProductRepository elasticProductRepository;

    @Test
    @DisplayName("라벨 등록")
    void createLabelSuccess() {
        LabelCreateRequest labelRequest = new LabelCreateRequest();
        ReflectionTestUtils.setField(labelRequest, "name", "hello");
        given(labelRepository.save(any(Label.class))).willReturn(new Label(labelRequest));

        labelService.createLabel(labelRequest);

        then(labelRepository).should().save(any(Label.class));
    }

    @Test
    @DisplayName("라벨 조회")
    void retrieveLabels() {
        given(labelRepository.findAllLabels()).willReturn(
                List.of(new LabelRetrieveResponse(1L, "hello")));

        List<LabelRetrieveResponse> response = labelService.retrieveLabels();

        assertThat(response.get(0).getName()).isEqualTo("hello");
    }

    @Test
    @DisplayName("라벨 삭제 성공")
    void deleteLabelSuccess() {
        given(labelRepository.findById(anyLong())).willReturn(
                Optional.of(new Label(new LabelCreateRequest())));
        given(elasticProductRepository.findAllByLabelName(anyString())).willReturn(null);
        willDoNothing().given(labelRepository).delete(any(Label.class));

        labelService.deleteLabel(1L);

        then(labelRepository).should().delete(any(Label.class));
    }

    @Test
    @DisplayName("라벨 삭제 실패")
    void deleteLabelFail() {
        assertThatThrownBy(() -> labelService.deleteLabel(1L))
                .isInstanceOf(LabelNotFoundException.class);
    }

}
