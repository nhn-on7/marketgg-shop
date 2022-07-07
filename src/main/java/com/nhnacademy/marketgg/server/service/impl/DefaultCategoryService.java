package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.repository.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCategoryService {

    private final CategoryRepository categoryRepository;
    private final CategorizationRepository categorizationRepository;
}
