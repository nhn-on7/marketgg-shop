package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.LabelCreateRequest;

import com.nhnacademy.marketgg.server.dto.response.LabelRetrieveResponse;
import java.util.List;

public interface LabelService {
    List<LabelRetrieveResponse> retrieveLabels();

    void createLabel(final LabelCreateRequest labelCreateRequest);

    void deleteLabel(final Long id);

}
