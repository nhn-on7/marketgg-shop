package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.marketgg.server.dto.request.LabelCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.LabelRetrieveResponse;
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

    @Test
    @DisplayName("라벨 등록")
    void createLabelSuccess() {
        LabelCreateRequest labelRequest = new LabelCreateRequest();
        ReflectionTestUtils.setField(labelRequest, "name", "hello");
        when(labelRepository.save(any(Label.class))).thenReturn(new Label(labelRequest));

        labelService.createLabel(labelRequest);

        verify(labelRepository, times(1)).save(any(Label.class));
    }

    @Test
    @DisplayName("라벨 조회")
    void retrieveLabels() {
        when(labelRepository.findAllLabels()).thenReturn(List.of(new LabelRetrieveResponse(1L, "hello")));

        List<LabelRetrieveResponse> response = labelService.retrieveLabels();

        assertThat(response.get(0).getName()).isEqualTo("hello");
    }

    @Test
    @DisplayName("라벨 삭제 성공")
    void deleteLabelSuccess() {
        when(labelRepository.findById(anyLong())).thenReturn(
            Optional.of(new Label(new LabelCreateRequest())));
        doNothing().when(labelRepository).delete(any(Label.class));

        labelService.deleteLabel(1L);

        verify(labelRepository, times(1)).delete(any(Label.class));
    }

    @Test
    @DisplayName("라벨 삭제 실패")
    void deleteLabelFail() {
        assertThatThrownBy(() -> labelService.deleteLabel(1L))
            .isInstanceOf(LabelNotFoundException.class);
    }

}
