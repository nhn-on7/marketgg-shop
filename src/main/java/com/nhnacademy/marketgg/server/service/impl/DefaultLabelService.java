package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.LabelCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.LabelRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.elastic.document.ElasticProduct;
import com.nhnacademy.marketgg.server.elastic.repository.ElasticProductRepository;
import com.nhnacademy.marketgg.server.entity.Label;
import com.nhnacademy.marketgg.server.exception.label.LabelNotFoundException;
import com.nhnacademy.marketgg.server.repository.label.LabelRepository;
import com.nhnacademy.marketgg.server.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultLabelService implements LabelService {

    private final LabelRepository labelRepository;
    private final ElasticProductRepository elasticProductRepository;

    @Transactional
    @Override
    public void createLabel(final LabelCreateRequest labelCreateRequest) {
        Label label = new Label(labelCreateRequest);

        labelRepository.save(label);
    }

    @Override
    public List<LabelRetrieveResponse> retrieveLabels() {
        return labelRepository.findAllLabels();
    }

    @Transactional
    @Override
    public void deleteLabel(final Long id) {
        Label label = labelRepository.findById(id).orElseThrow(LabelNotFoundException::new);

        List<ElasticProduct> esProducts = elasticProductRepository.findAllByLabelName(label.getName());
        for (ElasticProduct esProduct : esProducts) {
            esProduct.setLabelName(null);
            elasticProductRepository.save(esProduct);
        }

        labelRepository.delete(label);

    }

}
