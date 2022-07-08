package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.CategoryCreateRequest;

import java.util.List;

public interface CategoryService {

    void createCategory(final CategoryCreateRequest categoryCreateRequest);
    
    List<CategoryRetrieveResponse> retrieveCategories();

}
