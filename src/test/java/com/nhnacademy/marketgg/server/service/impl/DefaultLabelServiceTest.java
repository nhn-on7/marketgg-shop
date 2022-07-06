package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.LabelDto;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.exception.LabelNotFoundException;
import com.nhnacademy.marketgg.server.repository.LabelRepository;
import com.nhnacademy.marketgg.server.service.LabelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class DefaultLabelServiceTest {

    @Autowired
    private LabelService labelService;

    @MockBean
    private LabelRepository labelRepository;

    @Test
    @DisplayName("라벨 조회")
    void retrieveLabels() {
        when(labelRepository.getAll()).thenReturn(List.of(new LabelDto("hello")));

        List<LabelDto> response = labelService.retrieveLabels();

        assertThat(response.get(0).getName()).isEqualTo("hello");
    }

    @Test
    @DisplayName("라벨 등록 성공")
    void createLabelSuccess() {
        doNothing().when(labelRepository).save(any());

        labelService.createLabel(new LabelDto("hello"));

        verify(labelRepository, times(1)).save(any(Label.class));
    }

    @Test
    @DisplayName("라벨 삭제 성공")
    void deleteLabelSuccess() {
        when(labelRepository.findById(anyLong())).thenReturn(Optional.of(new Label(new LabelDto("hello"))));
        doNothing().when(labelRepository).delete(any());

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
