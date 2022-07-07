package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.LabelCreateRequest;

import com.nhnacademy.marketgg.server.dto.LabelRetrieveResponse;
import java.util.List;

public interface LabelService {
    List<LabelRetrieveResponse> retrieveLabels();

    void createLabel(LabelCreateRequest labelCreateRequest);

    void deleteLabel(Long id);
}
