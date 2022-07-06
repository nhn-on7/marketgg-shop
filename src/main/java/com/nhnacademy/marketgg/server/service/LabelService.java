package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.LabelDto;

import java.util.List;

public interface LabelService {
    List<LabelDto> retrieveLabels();

    void createLabel(LabelDto labelDto);

    void deleteLabel(Long id);
}
