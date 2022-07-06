package com.nhnacademy.marketgg.server.service;

import com.nhnacademy.marketgg.server.dto.CategoryRequest;

public interface CategoryService {

    void createCategory(CategoryRequest categoryRequest);

    void updateCategory(Long id, CategoryRequest categoryRequest);
}
