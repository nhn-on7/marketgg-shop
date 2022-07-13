package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.LabelCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.LabelRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.exception.LabelNotFoundException;
import com.nhnacademy.marketgg.server.repository.LabelRepository;
import com.nhnacademy.marketgg.server.service.LabelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class DefaultLabelServiceTest {

    @Autowired
    LabelService labelService;

    @MockBean
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
        when(labelRepository.findAllLabels()).thenReturn(List.of(new LabelRetrieveResponse("hello")));

        List<LabelRetrieveResponse> response = labelService.retrieveLabels();

        assertThat(response.get(0).getName()).isEqualTo("hello");
    }

    @Test
    @DisplayName("라벨 삭제 성공")
    void deleteLabelSuccess() {
        when(labelRepository.findById(anyLong())).thenReturn(Optional.of(new Label(new LabelCreateRequest())));
        doNothing().when(labelRepository).delete(any(Label.class));

        labelService.deleteLabel(1L);

        verify(labelRepository, times(1)).delete(any(Label.class));
    }

    @Test
    @DisplayName("라벨 삭제 실패")
    void deleteLabelFail() {
        assertThatThrownBy(()->labelService.deleteLabel(1L))
                .isInstanceOf(LabelNotFoundException.class);
    }

}
