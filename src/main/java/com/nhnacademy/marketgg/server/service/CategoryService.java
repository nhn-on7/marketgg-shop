package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {

    void createCategory(final CategoryCreateRequest categoryCreateRequest);

    List<CategoryRetrieveResponse> retrieveCategories();

    void updateCategory(final String categoryId, final CategoryUpdateRequest categoryRequest);

    void deleteCategory(final String categoryId);

}
