package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.LabelCreateRequest;
import com.nhnacademy.marketgg.server.dto.LabelRetrieveResponse;
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
    public void createLabel(LabelCreateRequest labelCreateRequest) {
        Label label = new Label(labelCreateRequest);

        labelRepository.save(label);
    }

    @Override
    public List<LabelRetrieveResponse> retrieveLabels() {
        return labelRepository.findAllLabels();
    }

    @Transactional
    @Override
    public void deleteLabel(Long id) {
        Label label = labelRepository.findById(id).orElseThrow(()->new LabelNotFoundException("라벨을 찾을 수 없습니다."));

        labelRepository.delete(label);
    }

}
