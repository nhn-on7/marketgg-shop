package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.LabelDto;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.exception.LabelNotFoundException;
import com.nhnacademy.marketgg.server.repository.LabelRepository;
import com.nhnacademy.marketgg.server.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultLabelService implements LabelService {

    private final LabelRepository labelRepository;

    @Transactional
    @Override
    public void createLabel(LabelDto labelDto) {
        Label label = new Label(labelDto);

        labelRepository.save(label);
    }

    @Override
    public List<LabelDto> retrieveLabels() {
        return labelRepository.findAllLabels();
    }

    @Transactional
    @Override
    public void deleteLabel(Long id) {
        Label label = labelRepository.findById(id).orElseThrow(()->new LabelNotFoundException("라벨을 찾을 수 없습니다."));

        labelRepository.delete(label);
    }

}
