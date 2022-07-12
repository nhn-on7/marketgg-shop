package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.request.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {

    void createCategory(final CategoryCreateRequest categoryCreateRequest);

    CategoryRetrieveResponse retrieveCategory(final String id);

    List<CategoryRetrieveResponse> retrieveCategories();

    void updateCategory(final String id, final CategoryUpdateRequest categoryRequest);

    void deleteCategory(final String id);

}
