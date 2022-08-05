package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.response.category.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.server.repository.categorization.CategorizationRepository;
import com.nhnacademy.marketgg.server.service.CategorizationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCategorizationService implements CategorizationService {

    private final CategorizationRepository categorizationRepository;

    @Override
    public List<CategorizationRetrieveResponse> retrieveCategorizations() {
        return categorizationRepository.findAllCategorization();
    }

}
